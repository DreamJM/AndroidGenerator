package com.wafa.android.pei.data;

import com.wafa.android.pei.data.net.TestApi;
import com.wafa.android.pei.model.Datum;
import rx.Observable;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

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
@Singleton
public class TestRepository {

    TestApi testApi;

    @Inject
    public TestRepository(TestApi testApi) {
        this.testApi = testApi;
    }

    public Observable<List<Datum>> getTestData() {
        return testApi.getTestData();
    }

}
