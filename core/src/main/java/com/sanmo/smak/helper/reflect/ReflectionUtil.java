package com.sanmo.smak.helper.reflect;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* 3 反射操作字节码 */
public class ReflectionUtil {

    private static final Logger logger= LoggerFactory.getLogger(ReflectionUtil.class);

    /*实例化*/
    public static Object newInstance(Class<?> cls){
        Object instance=null;
        try {
            instance=cls.getConstructor().newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return instance;
    }

    /*调用方法*/
    public static Object invokeMethod(Object obj, Method method,Object...args){
        Object result=null;
            method.setAccessible(true);
        try {
            result= method.invoke(obj,args);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return result;
    }

    /*设置域的值*/
    public static void setField(Object obj, Field field,Object value){
        field.setAccessible(true);
        try {
            field.set(obj,value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


}
