package deso1.dinhtrongdat.moviestream.model;


import java.io.Serializable;
import java.util.List;

public class User implements Serializable {
    private String username, password,name,avatar;
    private List<CategoryItem> favorite;

    public User() {
    }

    public User(String username, String password, String name, List<CategoryItem> favoriteMovie,String img) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.favorite = favoriteMovie;
        this.avatar=img;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CategoryItem> getFavorite() {
        return favorite;
    }

    public void setFavorite(List<CategoryItem> favoriteMovie) {
        this.favorite = favoriteMovie;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String img) {
        this.avatar = img;
    }
}
