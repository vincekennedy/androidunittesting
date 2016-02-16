package com.gdg.unittesting;

import rx.Observable;

import java.util.List;

public class RepositoryInteractor {

    final GitHub api;

    RepositoryInteractor(GitHub api){
       this.api = api;
    }

    public RepositoryInteractor(){
       this.api = GitHubService.getService();
    }

    Observable<List<Repository>> getRepositories(){
        return getRepositories("swanson");
    }

    Observable<List<Repository>> getRepositories(String user){
        return api.repositories(user);
    }
}
