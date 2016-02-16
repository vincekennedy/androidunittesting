package com.gdg.unittesting;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

import java.util.List;

public interface GitHub {

    @GET("/users/{user}/repos")
    Observable<List<Repository>> repositories( @Path("user") String username );
}
