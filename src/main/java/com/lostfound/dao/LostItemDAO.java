package com.lostfound.dao;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

public class LostItemDAO {
	private final MongoCollection<Document> col;

	public LostItemDAO() {
		try {
			System.out.println("🔍 Initializing LostItemDAO...");
			MongoDatabase db = DBUtil.getDatabase();
			this.col = db.getCollection("lostitems");
			System.out.println("✅ Successfully connected to 'lostitems' collection");
			
			// Create indexes if they don't exist
			try {
				// Create an index on userId for faster lookups
				col.createIndex(new Document("userId", 1));
				System.out.println("🔑 Created index on 'userId' field");
			} catch (Exception e) {
				System.err.println("⚠️ Could not create index: " + e.getMessage());
			}
		} catch (Exception e) {
			System.err.println("❌ Failed to initialize LostItemDAO: " + e.getMessage());
			throw e;
		}
	}

	// insert a lost item, returns inserted ObjectId string
	public String insertLostItem(String userId, String itemName, String description, String location, String date) {
		return insertLostItem(userId, itemName, description, location, date, null);
	}

	public String insertLostItem(String userId, String itemName, String description, String location, String date, String imageUrl) {
		System.out.println("📝 Inserting new lost item...");
		System.out.println("   User ID: " + userId);
		System.out.println("   Item: " + itemName);
		
		if (userId == null || itemName == null || description == null || location == null || date == null) {
			String error = "❌ All parameters must be non-null";
			System.err.println(error);
			throw new IllegalArgumentException(error);
		}
		
		try {
			Document d = new Document("userId", userId)
					.append("itemName", itemName)
					.append("description", description)
					.append("location", location)
					.append("date", date)
					.append("imageUrl", imageUrl)
					.append("createdAt", new java.util.Date());
			
			System.out.println("📄 Inserting document: " + d.toJson());
			col.insertOne(d);
			
			ObjectId id = d.getObjectId("_id");
			if (id == null) {
				String error = "❌ Failed to generate ID for the inserted document";
				System.err.println(error);
				throw new IllegalStateException(error);
			}
			String idStr = id.toHexString();
			System.out.println("✅ Successfully inserted lost item with ID: " + idStr);
			return idStr;
		} catch (Exception e) {
			System.err.println("Error inserting lost item: " + e.getMessage());
			throw new RuntimeException("Failed to insert lost item", e);
		}
	}

	// get all lost items (simple)
	public List<Document> getAllLostItems() {
		List<Document> list = new ArrayList<>();
		col.find().into(list);
		return list;
	}

	// get lost items for specific user
	public List<Document> getLostItemsByUser(String userId) {
		List<Document> list = new ArrayList<>();
		if (userId == null || userId.isEmpty()) return list;
		col.find(Filters.eq("userId", userId)).into(list);
		return list;
	}

	// find lost item by id
	public Document findById(String id) {
		return col.find(Filters.eq("_id", new ObjectId(id))).first();
	}

	// basic search	// search lost items by name and location (case-insensitive regex)
	public List<Document> search(String itemName, String location) {
		try {
			List<Bson> filters = new ArrayList<>();
			
			// Add item name filter if provided
			if (itemName != null && !itemName.trim().isEmpty()) {
				filters.add(Filters.regex("itemName", "(?i).*" + itemName + ".*"));
			}
			
			// Add location filter if provided
			if (location != null && !location.trim().isEmpty()) {
				filters.add(Filters.regex("location", "(?i).*" + location + ".*"));
			}
			
			// Create the final query
			Bson query = filters.isEmpty() ? new Document() : Filters.and(filters);
			
			// Execute the query
			List<Document> results = new ArrayList<>();
			col.find(query).into(results);
			
			System.out.println(" Found " + results.size() + " lost items matching the search criteria");
			return results;
			
		} catch (Exception e) {
			System.err.println(" Error searching lost items: " + e.getMessage());
			throw new RuntimeException("Failed to search lost items", e);
		}
	}
}
