package deso1.dinhtrongdat.moviestream.model;

import java.io.Serializable;
import java.util.List;

public class AllCategory implements Serializable {
    private String cateTitle;
    private int cateId;
    List<CategoryItem> listCategoryItem;

    public AllCategory() {
    }

    public AllCategory(int cateId, String cateTitle,List<CategoryItem> listCategoryItem) {
        this.cateTitle = cateTitle;
        this.cateId = cateId;
        this.listCategoryItem = listCategoryItem;
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

    public List<CategoryItem> getListCategoryItem() {
        return listCategoryItem;
    }

    public void setListCategoryItem(List<CategoryItem> listCategoryItem) {
        this.listCategoryItem = listCategoryItem;
    }
}
