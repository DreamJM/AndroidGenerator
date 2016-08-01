package com.dream.android.sample.data;

import com.dream.android.sample.data.net.GithubApi;
import com.dream.android.sample.model.Contributor;
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
public class GithubRepository {

    GithubApi githubApi;

    @Inject
    public GithubRepository(GithubApi githubApi) {
        this.githubApi = githubApi;
    }

    public Observable<List<Contributor>> getContributors(String owner, String repo) {
        return githubApi.getContributors(owner, repo);
    }

}
