package com.gdg.unittesting;

import com.google.gson.annotations.SerializedName;

public class RepositoryOwner {
    @SerializedName("login")
    public String username;

    public RepositoryOwner(String username) {
        this.username = username;
    }

    public boolean isCool() {
        return username == "swanson";
    }
}
