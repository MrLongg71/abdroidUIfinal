package vn.mrlongg71.assignment.Model;

public class User {
    private String user;
    private String email;
    private String image;

    public User() {
    }

    public User(String user, String email, String image) {
        this.user = user;
        this.email = email;

        this.image = image;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
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
