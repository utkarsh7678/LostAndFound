package com.lostfound.dao;

import org.bson.Document;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class DBUtil {
    private static MongoDatabase database;
    private static MongoClient mongoClient;
    private static boolean connectionFailed = false;

    // call DBUtil.getDatabase() from DAOs
    public static synchronized MongoDatabase getDatabase() {
        if (database == null && !connectionFailed) {
            try {
                String uri = System.getenv("MONGODB_URI");
                if (uri == null || uri.trim().isEmpty()) {
                    // MongoDB Atlas connection string using raw password (no encoding)
                    String username = "utkarsh";
                    String password = "x1pdnYqTbFsN9Zpg";

                    StringBuilder connectionStringBuilder = new StringBuilder();
                    connectionStringBuilder.append("mongodb+srv://");
                    connectionStringBuilder.append(username);
                    connectionStringBuilder.append(":");
                    connectionStringBuilder.append(password); // no encoding
                    connectionStringBuilder.append("@cluster0.rweelvt.mongodb.net/");
                    connectionStringBuilder.append("lost_found_portal");
                    // Use Atlas default auth DB (admin); remove this param if your user is scoped differently
                    connectionStringBuilder.append("?authSource=admin");
                    connectionStringBuilder.append("&retryWrites=true");
                    connectionStringBuilder.append("&w=majority");

                    uri = connectionStringBuilder.toString();
                }

                // Create a new client and connect to the server
                System.out.println("🔌 Attempting to connect to MongoDB...");
                System.out.println("🔗 Connection string: " + uri.replaceAll(":([^:@/\\?]+)@", ":*****@"));

                ConnectionString connectionString = new ConnectionString(uri);
                MongoClientSettings settings = MongoClientSettings.builder()
                    .applyConnectionString(connectionString)
                    .build();

                mongoClient = MongoClients.create(settings);

                // Extract DB name from URI or default to 'lost_found_portal'
                String dbName = "lost_found_portal";
                String connectionStringUri = connectionString.getConnectionString();
                int lastSlash = connectionStringUri.lastIndexOf("/");
                if (lastSlash != -1 && connectionStringUri.length() > lastSlash + 1) {
                    String after = connectionStringUri.substring(lastSlash + 1);
                    int q = after.indexOf("?");
                    if (q > -1) after = after.substring(0, q);
                    if (!after.isEmpty()) dbName = after;
                }

                database = mongoClient.getDatabase(dbName);

                // Test the connection
                System.out.println("🔍 Testing database connection...");
                Document pingResult = database.runCommand(new Document("ping", 1));
                System.out.println("✅ Successfully connected to MongoDB! Database: " + dbName);
                System.out.println("📊 Server info: " + pingResult.toJson());

                // List all collections
                System.out.println("📋 Available collections:");
                for (String collectionName : database.listCollectionNames()) {
                    System.out.println("   - " + collectionName);
                }

            } catch (Exception e) {
                System.err.println("❌ Error connecting to MongoDB: " + e.getMessage());
                if (e.getCause() != null) {
                    System.err.println("   Caused by: " + e.getCause().getMessage());
                }
                connectionFailed = true;
                throw new RuntimeException("Failed to connect to MongoDB", e);
            }
        }

        if (database == null) {
            throw new RuntimeException("MongoDB connection not available");
        }

        return database;
    }

    public static synchronized void close() {
        if (mongoClient != null) {
            try {
                mongoClient.close();
                System.out.println("🔌 Disconnected from MongoDB");
            } catch (Exception e) {
                System.err.println("❌ Error closing MongoDB connection: " + e.getMessage());
            } finally {
                mongoClient = null;
                database = null;
                connectionFailed = false;
            }
        }
    }

    public static boolean isConnected() {
        return database != null && !connectionFailed;
    }
}



