package com.wafa.android.pei.interactor;

import com.wafa.android.pei.data.TestRepository;
import com.wafa.android.pei.lib.executor.BaseCase;
import com.wafa.android.pei.lib.executor.JobExecutor;
import com.wafa.android.pei.lib.executor.UIThread;
import rx.Observer;

import javax.inject.Inject;

/**
 * Description:
 * <p>
 * Copyright: Copyright (c) 2016, All rights reserved.
 * <p>
 * Company:中配联电子商务南京有限公司
 *
 * @author snovajiang
 * @date 16/6/3
 */
public class TestCase extends BaseCase {

    TestRepository testRepository;

    @Inject
    public TestCase(JobExecutor threadExecutor, UIThread postExecutionThread, TestRepository testRepository) {
        super(threadExecutor, postExecutionThread);
        this.testRepository = testRepository;
    }

    public void execute(Observer observer) {
        execute(testRepository.getTestData(), observer);
    }
}
