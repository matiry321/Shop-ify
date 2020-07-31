package com.example.theonlinegrocerystore;

public class Review {
    private int groceryItemId;
    private String userName;
    private String text;
    private String date;

    public Review(int groceryItemId, String name, String userName, String date) {
        this.groceryItemId = groceryItemId;
        this.userName = userName;
        this.date = date;
    }

    public int getGroceryItemId() {
        return groceryItemId;
    }

    public void setGroceryItemId(int groceryItemId) {
        this.groceryItemId = groceryItemId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDate() {
        return date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Review{" +
                "groceryItemId=" + groceryItemId +
                ", userName='" + userName + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
