package com.semicode.findsolution.models;

import java.io.Serializable;
import java.util.List;

public class Slider_Model implements Serializable {
    private List<Data> data;

    public List<Data> getData() {
        return data;
    }

    public class Data implements Serializable {
        private String image;
        private String type;

        public String getImage() {
            return image;
        }

        public String getType() {
            return type;
        }
    }
}
