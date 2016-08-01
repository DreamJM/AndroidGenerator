package com.dream.android.sample.interactor;

import com.dream.android.sample.data.GithubRepository;
import com.dream.android.sample.lib.executor.BaseCase;
import com.dream.android.sample.lib.executor.JobExecutor;
import com.dream.android.sample.lib.executor.UIThread;
import com.dream.android.sample.model.Contributor;
import rx.Observer;
import rx.Subscriber;

import javax.inject.Inject;
import java.util.List;

/**
 *
 * Copyright: Copyright (c) 2016, All rights reserved.
 *
 * @author Dream
 * @date 16/6/3
 */
public class GetContributorsCase extends BaseCase {

    GithubRepository githubRepository;

    @Inject
    public GetContributorsCase(JobExecutor threadExecutor, UIThread postExecutionThread, GithubRepository githubRepository) {
        super(threadExecutor, postExecutionThread);
        this.githubRepository = githubRepository;
    }

    public void execute(String owner, String repo, Observer<List<Contributor>> subscriber) {
        execute(githubRepository.getContributors(owner, repo), subscriber);
    }
}
