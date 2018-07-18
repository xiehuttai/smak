package com.sanmo.smak.core;

import com.sanmo.smak.helper.bean.BeanHelper;
import com.sanmo.smak.helper.clazz.ClassHelper;
import com.sanmo.smak.helper.clazz.ClassUtil;
import com.sanmo.smak.helper.controller.ControllerHelper;
import com.sanmo.smak.helper.ioc.IocHelper;

/*7 加载相应helper*/
public final class Loader {

    public static void  init(){
        Class<?>[] classList ={
            ClassHelper.class, BeanHelper.class,IocHelper.class,ControllerHelper.class
        };
        for (Class<?> cls: classList)
            ClassUtil.loadClass(cls.getName());

    }


}
