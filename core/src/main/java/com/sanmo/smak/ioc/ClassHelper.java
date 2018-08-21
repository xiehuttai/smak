package com.sanmo.smak.ioc;

import com.sanmo.smak.annotation.Controller;
import com.sanmo.smak.annotation.Service;
import com.sanmo.smak.config.ConfigHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

/* 2.1 获取应用路径下的指定bean的clazz */
public final class ClassHelper {

    private static final Logger logger = LoggerFactory.getLogger(ClassHelper.class);

    private static final Set<Class<?>> CLASS_SET;

    static {
        String basePackage=ConfigHelper.getBasePath();
        CLASS_SET = ClassUtil.getClassSet(basePackage);
        logger.info("basePackage:{}",basePackage);
        logger.info("classSet:{},",CLASS_SET);
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

    /**
     * 得到指定类的所有子类
     */
    public static Set<Class<?>> getClassSetBySuper(Class<?> superClass) {
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        for (Class<?> cls:CLASS_SET)
            if (superClass.isAssignableFrom(cls)&&!superClass.equals(cls))
                classSet.add(cls);

        return classSet;
    }

    /**
     * 得到指定注解的所有子类
     */
    public static Set<Class<?>> getClassSetByAnnotation(Class<? extends Annotation> annotationClass) {
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        for (Class<?> cls:CLASS_SET)
            if (cls.isAnnotationPresent(annotationClass))
                classSet.add(cls);
        return classSet;
    }

}
