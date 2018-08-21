package com.sanmo.smak;

import com.sanmo.smak.aop.AopHelper;
import com.sanmo.smak.ioc.BeanHelper;
import com.sanmo.smak.ioc.ClassHelper;
import com.sanmo.smak.ioc.ClassUtil;
import com.sanmo.smak.mvc.controller.ControllerHelper;
import com.sanmo.smak.ioc.IocHelper;

/*7 加载相应helper*/
public final class Loader {

    public static void  init(){
        Class<?>[] classList ={
                ClassHelper.class,
                BeanHelper.class,
                IocHelper.class,
                ControllerHelper.class,
                AopHelper.class
        };
        for (Class<?> cls: classList)
            ClassUtil.loadClass(cls.getName());

    }
}
