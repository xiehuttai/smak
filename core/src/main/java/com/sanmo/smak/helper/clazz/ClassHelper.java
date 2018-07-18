package com.sanmo.smak.helper.clazz;

import com.sanmo.smak.annotation.Controller;
import com.sanmo.smak.annotation.Service;
import com.sanmo.smak.helper.config.ConfigHelper;

import java.util.HashSet;
import java.util.Set;

/* 2.1 获取应用路径下的指定bean的clazz */
public final class ClassHelper {

    private static final Set<Class<?>> CLASS_SET;

    static {
        String basePackage=ConfigHelper.getBasePath();
        CLASS_SET = ClassUtil.getClassSet(basePackage);
    }

    public static Set<Class<?>> getClassSet(){
        return CLASS_SET;
    }

    public static Set<Class<?>> getServiceClassSet(){
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        for (Class<?> cls: CLASS_SET)
            if (cls.isAnnotationPresent(Service.class))
                classSet.add(cls);
        return classSet;
    }

    public static Set<Class<?>> getControllerClassSet(){
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        for (Class<?> cls:CLASS_SET)
            if (cls.isAnnotationPresent(Controller.class))
                classSet.add(cls);
        return classSet;
    }

    public static Set<Class<?>> getBeanClassSet(){
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        classSet.addAll(getServiceClassSet());
        classSet.addAll(getControllerClassSet());
        return classSet;
    }

}
