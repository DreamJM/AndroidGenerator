package com.dream.android.generator;

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
public class AndroidGenerator {

    public static void main(String[] args) {
        if(args != null && args.length > 0) {
            if("mvp".equals(args[0].toLowerCase())) {
                generateMvp();
            } else if("normal".equals(args[0].toLowerCase())) {
                generateNormal();
            } else {
                generateMVVM();
            }
        } else { //默认生成MVVM框架
            generateMVVM();
        }
    }

    private static void generateMvp() {
        BaseBuilder.getInstance().prepareProject();
        BaseBuilder.getInstance().createMVPLibrary();
        BaseBuilder.getInstance().createMVPApp();
    }

    private static void generateMVVM() {
        BaseBuilder.getInstance().prepareProject();
        BaseBuilder.getInstance().createMVVMLibrary();
        BaseBuilder.getInstance().createMVVMApp();
    }

    private static void generateNormal() {
        BaseBuilder.getInstance().prepareProject();
        BaseBuilder.getInstance().createLibrary();
        BaseBuilder.getInstance().createApp();
    }
}
