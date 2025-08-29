package com.lostfound.dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

public class FoundItemDAO {
    private final MongoCollection<Document> col;

    public FoundItemDAO() {
        MongoDatabase db = DBUtil.getDatabase();
        col = db.getCollection("founditems");
    }

    // insert found item (may be used later)
    public String insertFoundItem(String userId, String itemName, String description, String location, String date, String imageUrl) {
        Document d = new Document("userId", userId)
                .append("itemName", itemName)
                .append("description", description)
                .append("location", location)
                .append("date", date)
                .append("imageUrl", imageUrl);
        col.insertOne(d);
        return d.getObjectId("_id").toHexString();
    }

    // find potential matches by itemName and location using regex (case-insensitive)
    public List<Document> findPotentialMatches(String itemName, String location) {
        List<Bson> filters = new ArrayList<>();
        if (itemName != null && !itemName.trim().isEmpty()) {
            filters.add(Filters.regex("itemName", "(?i).*" + itemName + ".*"));
        }
        if (location != null && !location.trim().isEmpty()) {
            filters.add(Filters.regex("location", "(?i).*" + location + ".*"));
        }
        Bson f = filters.isEmpty() ? new Document() : Filters.and(filters);
        List<Document> list = new ArrayList<>();
        col.find(f).into(list);
        return list;
    }
    
    public List<Document> search(String itemName, String location) {
        return findPotentialMatches(itemName, location);
    }
    
    public List<Document> getAllFoundItems() {
        return col.find().into(new ArrayList<>());
    }
}
