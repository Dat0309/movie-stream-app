package deso1.dinhtrongdat.moviestream.model;

import java.io.Serializable;

public class AllCategory implements Serializable {
    private String cateTitle;
    private int cateId;

    public AllCategory() {
    }

    public AllCategory(int cateId, String cateTitle) {
        this.cateTitle = cateTitle;
        this.cateId = cateId;
    }

    public String getCateTitle() {
        return cateTitle;
    }

    public void setCateTitle(String cateTitle) {
        this.cateTitle = cateTitle;
    }

    public int getCateId() {
        return cateId;
    }

    public void setCateId(int cateId) {
        this.cateId = cateId;
    }
}
