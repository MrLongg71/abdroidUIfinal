package vn.mrlongg71.assignment.Model;

public class User {
    private String userID;
    private String username;
    private String email;
    private String image;

    public User() {
    }

    public User(String userID, String username, String email, String image) {
        this.userID = userID;
        this.username = username;
        this.email = email;
        this.image = image;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}