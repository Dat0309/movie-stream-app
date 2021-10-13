package deso1.dinhtrongdat.moviestream.model;

import java.io.Serializable;

public class BannerMovie implements Serializable {
    private int id;
    private String movieName;
    private String imgUrl;
    private String fileUrl;

    public BannerMovie() {
    }

    public BannerMovie(int id, String movieName, String imgUrl, String fileUrl) {
        this.id = id;
        this.movieName = movieName;
        this.imgUrl = imgUrl;
        this.fileUrl = fileUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }
}
