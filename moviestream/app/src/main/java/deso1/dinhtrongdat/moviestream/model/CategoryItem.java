package deso1.dinhtrongdat.moviestream.model;

import java.io.Serializable;

public class CategoryItem implements Serializable {
    private long id;
    private String name;
    private String img;
    private String video;
    private String type;

    public CategoryItem() {
    }

    public CategoryItem(long id,String imgUrl,String name,String type,String fileUrl) {
        this.id = id;
        this.name = name;
        this.img = imgUrl;
        this.video = fileUrl;
        this.type = type;
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

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
