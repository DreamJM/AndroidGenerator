package test;

/**
 * Description:自动生成数据库，并覆盖指定路径下的DAO文件
 *
 * Copyright: Copyright (c) 2016, All rights reserved.
 *
 * @author jiangm
 * @date 16/5/27
 */
import de.greenrobot.daogenerator.*;

public class DaoGen {

    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(1, "<jm PACKAGE>.data.cache.db");
        new DaoGenerator().generateAll(schema, "app/main/src/java/<jm PACKAGE_PATH>/data/db");
    }
}
