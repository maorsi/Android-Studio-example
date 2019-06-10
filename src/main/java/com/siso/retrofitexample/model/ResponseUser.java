package com.siso.retrofitexample.model;

import com.google.gson.annotations.SerializedName;

public class ResponseUser {

    @SerializedName("data")
    User user;

    public ResponseUser() {

    }

    public ResponseUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
