package com.lostfound.dao;

import org.bson.Document;

import com.lostfound.models.User;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class UserDAO {

    private MongoCollection<Document> userCollection;

    public UserDAO() {
        try {
            MongoDatabase db = DBUtil.getDatabase();
            userCollection = db.getCollection("users");
        } catch (Exception e) {
            System.err.println("❌ Error initializing UserDAO: " + e.getMessage());
            throw new RuntimeException("Failed to initialize UserDAO", e);
        }
    }

    public void registerUser(User user) {
        try {
            if (userCollection == null) {
                throw new RuntimeException("Database connection not available");
            }
            
            Document doc = new Document("name", user.getName())
                    .append("email", user.getEmail())
                    .append("password", user.getPassword());
            userCollection.insertOne(doc);
            System.out.println("✅ User Registered: " + user.getEmail());
        } catch (Exception e) {
            System.err.println("❌ Error registering user: " + e.getMessage());
            throw new RuntimeException("Failed to register user", e);
        }
    }

    public boolean loginUser(String email, String password) {
        try {
            if (userCollection == null) {
                throw new RuntimeException("Database connection not available");
            }
            
            Document user = userCollection.find(new Document("email", email)
                    .append("password", password)).first();
            return user != null;
        } catch (Exception e) {
            System.err.println("❌ Error during login: " + e.getMessage());
            throw new RuntimeException("Failed to authenticate user", e);
        }
    }
}

