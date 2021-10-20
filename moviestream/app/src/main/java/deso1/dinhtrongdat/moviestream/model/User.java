package deso1.dinhtrongdat.moviestream.model;


import java.io.Serializable;
import java.util.List;

public class User implements Serializable {
    private String username, password,name, img;
    private List<CategoryItem> favoriteMovie;

    public User() {
    }

    public User(String username, String password, String name, List<CategoryItem> favoriteMovie) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.favoriteMovie = favoriteMovie;
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

    public List<CategoryItem> getFavoriteMovie() {
        return favoriteMovie;
    }

    public void setFavoriteMovie(List<CategoryItem> favoriteMovie) {
        this.favoriteMovie = favoriteMovie;
    }
}
