package com.lostfound.dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class MatchDAO {
    private final MongoCollection<Document> col;

    public MatchDAO() {
        MongoDatabase db = DBUtil.getDatabase();
        col = db.getCollection("matches");
    }

    // create a match entry (lostId and foundId are hex string ids)
    public void createMatch(String lostId, String foundId) {
        Document d = new Document("lostItemId", lostId)
                .append("foundItemId", foundId)
                .append("status", "open");
        col.insertOne(d);
        System.out.println("🔔 Match created for lost:" + lostId + " found:" + foundId);
    }
}

