package com.semicode.findsolution.models;

import java.io.Serializable;
import java.util.List;

public class AllCatogryModel implements Serializable {
    private List<SingleCategoryModel> data;

    public List<SingleCategoryModel> getData() {
        return data;
    }


}