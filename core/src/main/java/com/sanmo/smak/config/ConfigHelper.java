package com.sanmo.smak.config;

import org.apache.commons.lang3.StringUtils;

import java.util.Properties;

public class ConfigHelper {

    private static final Properties CONFIG_PROPS=PropertiesUtil.loadProps(ConfigConstant.CONFIG_FILE);

    /* 获取jdbc */
    public static String getJdbcDriver(){
        return CONFIG_PROPS.getProperty(ConfigConstant.JDBC_DRIVER);
    }

    public static String getJdbcUrl(){
        return CONFIG_PROPS.getProperty(ConfigConstant.JDBC_URL);
    }

    public static String getJdbcUsername(){
        return CONFIG_PROPS.getProperty(ConfigConstant.JDBC_USERNAME);
    }

    public static String getJdbcPassword(){
        return CONFIG_PROPS.getProperty(ConfigConstant.JDBC_PASSWORD);
    }

    public static String getConfig(String key){
        return CONFIG_PROPS.getProperty(key);
    }


    /*获取应用相关配置*/
    public static String getJspPath(){
        String property = CONFIG_PROPS.getProperty(ConfigConstant.JSP_PATH);
        if (StringUtils.isEmpty(property))
            return "/WEB-INF/view/";
        return property;
    }

    public static String getBasePath(){
        return CONFIG_PROPS.getProperty(ConfigConstant.BASE_PACKAGE);
    }

    public static String getAssetPath(){
        String property = CONFIG_PROPS.getProperty(ConfigConstant.ASSET_PATH);
        if (StringUtils.isEmpty(property))
            return "/asset/";
        return property;
    }

}
