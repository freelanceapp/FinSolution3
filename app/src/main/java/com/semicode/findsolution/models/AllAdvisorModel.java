package com.semicode.findsolution.models;

import java.io.Serializable;
import java.util.List;

public class AllAdvisorModel implements Serializable {
    private List<SingleUserModel> data;

    public List<SingleUserModel> getData() {
        return data;
    }
}
