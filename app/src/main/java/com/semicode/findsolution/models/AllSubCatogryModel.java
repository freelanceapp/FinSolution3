package com.semicode.findsolution.models;

import java.io.Serializable;
import java.util.List;

public class AllSubCatogryModel implements Serializable {
    private List<SingleSubCategoryModel> data;
    private SingleCategoryModel parent_category;

    public List<SingleSubCategoryModel> getData() {
        return data;
    }

    public SingleCategoryModel getParent_category() {
        return parent_category;
    }
}