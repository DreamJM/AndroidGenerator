package com.wafa.android.pei.data.net;

import com.wafa.android.pei.lib.data.net.base.ObservableConverter;
import com.wafa.android.pei.lib.data.net.base.ServiceBuilder;
import com.wafa.android.pei.lib.model.ServerResult;
import com.wafa.android.pei.model.Datum;
import retrofit2.http.GET;
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
public class TestApi {

    private TestInterface testInterface;

    @Inject
    public TestApi(ServiceBuilder builder) {
        testInterface = builder.build(TestInterface.class);
    }

    public Observable<List<Datum>> getTestData() {
        return testInterface.getTestData().flatMap(new ObservableConverter<>());
    }

    interface TestInterface {
        /**
         *
         */
        @GET("app/open/business_scope.htm")
        Observable<ServerResult<List<Datum>>> getTestData();
    }

}
