package com.lostfound.models;

import org.bson.types.ObjectId;

public class LostItem {
    private ObjectId id;       // MongoDB ka _id
    private String userId;     // jisne report kiya uska userId
    private String itemName;   // item ka naam
    private String description; // item ka description
    private String location;    // kahan lost hua
    private String date;        // kis date ko lost hua

    public LostItem() {}

    public LostItem(String userId, String itemName, String description, String location, String date) {
        this.userId = userId;
        this.itemName = itemName;
        this.description = description;
        this.location = location;
        this.date = date;
    }

    // Getters & Setters
    public ObjectId getId() { return id; }
    public void setId(ObjectId id) { this.id = id; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getItemName() { return itemName; }
    public void setItemName(String itemName) { this.itemName = itemName; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
}
