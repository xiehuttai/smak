package com.sanmo.smak.ioc;

import com.sanmo.smak.annotation.Inject;
import org.apache.commons.collections4.MapUtils;

import java.lang.reflect.Field;
import java.util.Map;

/* 5 IOC init */
public class IocHelper {
    static {
        Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();
        if (MapUtils.isNotEmpty(beanMap)){
            for (Map.Entry<Class<?>,Object> entry: beanMap.entrySet()){
                Class<?> clazz = entry.getKey();
                Object instance = entry.getValue();
                Field[] declaredFields = clazz.getDeclaredFields();
                for (Field f: declaredFields){
                    if (f.isAnnotationPresent(Inject.class)){
                        Class<?> type = f.getType();
                        Object o = beanMap.get(type);
                        if(o!=null)
                            ReflectionUtil.setField(instance,f,o);
                    }
                }
            }
        }
    }
}
