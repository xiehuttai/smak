package com.sanmo.smak.framework.example.dao.util;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class CastUtil {

    private static final Logger logger = LoggerFactory.getLogger(CastUtil.class);

    public static Map objectToMap( Object object){
        if(object == null)
            return null;
        Map<String, Object> map = new HashMap<String, Object>();
        BeanInfo beanInfo = null;
        try {
            beanInfo = Introspector.getBeanInfo(object.getClass());
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor property : propertyDescriptors) {
            String key = property.getName();
            if (key.compareToIgnoreCase("class") == 0) {
                continue;
            }
            Method getter = property.getReadMethod();
            Object value = null;
            try {
                value = getter!=null ? getter.invoke(object) : null;
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            map.put(key, value);
        }

        return map;
    }

    public static long stringToLong(String s){
        long aLong = (long)Long.parseLong(s);
        return aLong;
    }

    public static Map<String,String> queryStringToMap(String queryString){
        HashMap<String, String> map = new HashMap<>();
        String[] strings = queryString.split("&");
        for (String s: strings){
            String[] split = s.split("=");
            map.put(split[0],split[1]);
        }
        return map;
    }

    public static String isoToUtf(String input){
        byte[] bytes = new byte[0];
        String s = null;
        try {
            s = new String( input.getBytes("ISO8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return s;
    }
}
