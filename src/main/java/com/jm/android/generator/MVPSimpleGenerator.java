package com.jm.android.generator;

/**
 * Description:
 * <p>
 * Copyright: Copyright (c) 2016, All rights reserved.
 * <p>
 * Company:中配联电子商务南京有限公司
 *
 * @author snovajiang
 * @date 16/5/26
 */
public class MVPSimpleGenerator {

    public static void main(String[] args) {
        BaseBuilder.getInstance().prepareProject();
        BaseBuilder.getInstance().createLibrary();
        BaseBuilder.getInstance().createApp();
    }

}
