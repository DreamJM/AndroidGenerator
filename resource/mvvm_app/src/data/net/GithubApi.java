package com.dream.android.sample.data.net;

import com.dream.android.sample.lib.data.net.base.ServiceBuilder;
import com.dream.android.sample.model.Contributor;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

/**
 *
 * Copyright: Copyright (c) 2016, All rights reserved.
 *
 * @author Dream
 * @date 16/6/3
 */
@Singleton
public class GithubApi {

    private GithubInterface githubInterface;

    @Inject
    public GithubApi(ServiceBuilder builder) {
        githubInterface = builder.build(GithubInterface.class);
    }

    public Observable<List<Contributor>> getContributors(String owner, String repo) {
        return githubInterface.contributors(owner, repo);
    }

    interface GithubInterface {
        /**
         *
         */
        @GET("/repos/{owner}/{repo}/contributors")
        Observable<List<Contributor>> contributors(@Path("owner") String owner, @Path("repo") String repo);
    }

}
