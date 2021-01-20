package com.semicode.findsolution.models;

import java.io.Serializable;
import java.util.List;

public class AllPackgesModel implements Serializable {
    private List<SinglePakcgesModel> data;

    public List<SinglePakcgesModel> getData() {
        return data;
    }
}
