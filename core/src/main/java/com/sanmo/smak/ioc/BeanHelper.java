package com.sanmo.smak.ioc;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/* 4 实例化bean*/
public class BeanHelper {

    private static final Map<Class<?>,Object> BEAN_MAP = new HashMap<Class<?>, Object>();

    static {
        Set<Class<?>> beanClassSet = ClassHelper.getBeanClassSet();
        for (Class<?> beanClass: beanClassSet){
            Object obj = ReflectionUtil.newInstance(beanClass);
            BEAN_MAP.put(beanClass,obj);
        }
    }

    public static Map<Class<?>,Object> getBeanMap(){
        return BEAN_MAP;
    }

    public static <T> T getBean(Class<T> cls){
        if (!BEAN_MAP.containsKey(cls))
            throw new RuntimeException("can not get bean by class : "+ cls);
        return (T)BEAN_MAP.get(cls);
    }

    /**
     * 设置bean实例
     * @param cls
     * @param obj
     */
    public static void setBean(Class<?> cls,Object obj ){
        BEAN_MAP.put(cls,obj);
    }

}
