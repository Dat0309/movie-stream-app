package deso1.dinhtrongdat.moviestream.model;

import java.io.Serializable;

public class Favorite implements Serializable {
    private String name,img,type,video, uid;
    private long id;

    public Favorite() {
    }

    public Favorite(long id, String name, String img, String type, String video, String uid) {
        this.id = id;
        this.name = name;
        this.img = img;
        this.type = type;
        this.video = video;
        this.uid = uid;

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
