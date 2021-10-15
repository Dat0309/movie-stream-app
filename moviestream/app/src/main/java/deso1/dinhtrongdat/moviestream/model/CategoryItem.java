package deso1.dinhtrongdat.moviestream.model;

import java.io.Serializable;

public class CategoryItem implements Serializable {
    private int id;
    private String name;
    private String imgUrl;
    private String fileUrl;

    public CategoryItem() {
    }

    public CategoryItem(int id, String name, String imgUrl,String fileUrl) {
        this.id = id;
        this.name = name;
        this.imgUrl = imgUrl;
        this.fileUrl = fileUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
