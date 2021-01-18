package com.semicode.findsolution.models;

import java.io.Serializable;
import java.util.List;

public class UserModel implements Serializable {
    private int status;

    private SingleUserModel data;

    public int getStatus() {
        return status;
    }

    public SingleUserModel getData() {
        return data;
    }



}
