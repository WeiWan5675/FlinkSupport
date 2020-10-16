package com.weiwan.support.core.constant;

import com.weiwan.support.common.constant.Constans;


/**
 * @Author: xiaozhennan
 * @Date: 2020/10/9 15:39
 * @Package: com.weiwan.support.core.constant.SupportConstants
 * @ClassName: SupportConstants
 * @Description:
 **/
public class SupportConstants {

    public static final String ETL_BASE_APP_CLASSNAME = "com.weiwan.support.etl.framework.app.ETLStreamBaseApp";

    public static final String SQL_BASE_APP_CLASSNAME = "com.weiwan.support.sql.framework.app.SQLStreamBaseApp";

    public static final String SUPPORT_WORKSPACE = "hdfs://flink_support_space";

    public static final String FLINK_HDFS_HOME_PREFIX = SUPPORT_WORKSPACE + Constans.SIGN_SLASH + "flink";

    public static final String SUPPORT_LIB_DIR = SUPPORT_WORKSPACE + Constans.SIGN_SLASH + "lib";

    public static final String SUPPORT_TMP_DIR = SUPPORT_WORKSPACE + Constans.SIGN_SLASH + "tmp";

    public static final String SUPPORT_ETL_SPACE = SUPPORT_WORKSPACE + Constans.SIGN_SLASH + "etl";

    public static final String SUPPORT_SQL_SPACE = SUPPORT_WORKSPACE + Constans.SIGN_SLASH + "sql";

    public static final String SUPPORT_CONF_DIR = SUPPORT_WORKSPACE + Constans.SIGN_SLASH + "conf";

    public static final String SUPPORT_RESOURCES_DIR = SUPPORT_WORKSPACE + Constans.SIGN_SLASH + "resources";


    public static final String KEY_SUPPORT_HOME = "FLINK_SUPPORT_HOME";
}
