package com.gdg.unittesting;


public class Repository {
    public String name;
    public String language;
    public RepositoryOwner owner;

    public Repository(String name, String language, RepositoryOwner owner) {
        this.name = name;
        this.language = language;
        this.owner = owner;
    }

    public String fullName() {
        return owner.username + "/" + name;
    }
}
