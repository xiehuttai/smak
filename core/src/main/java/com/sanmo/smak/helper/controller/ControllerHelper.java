package com.sanmo.smak.helper.controller;

import com.sanmo.smak.annotation.Action;
import com.sanmo.smak.helper.clazz.ClassHelper;
import com.sanmo.smak.helper.controller.bean.Handler;
import com.sanmo.smak.helper.controller.bean.Request;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/* 6 controller、action */
public class ControllerHelper {

    private static final Map<Request,Handler> ACTION_MAP= new HashMap<Request,Handler>();

    static {

        Set<Class<?>> controllerClassSet = ClassHelper.getControllerClassSet();

        for (Class<?> cls : controllerClassSet){
            Method[] methods = cls.getMethods();
            for(Method m: methods){
                if (m.isAnnotationPresent(Action.class)){
                    Action annotation = m.getAnnotation(Action.class);
                    String mapping = annotation.value();
                    if (mapping.matches("\\w+:/\\w*")){
                        String[] array = mapping.split(":");
                        if (ArrayUtils.isEmpty(array)&& array.length==2){
                            String requestMethod = array[0];
                            String requestPath = array[1];
                            Request request = new Request(requestMethod, requestPath);
                            Handler handler = new Handler(cls, m);
                            ACTION_MAP.put(request,handler);
                        }
                    }
                }
            }
        }
    }


    public static Handler getHandler(String requestMethod,String requestPath){
        Request request = new Request(requestMethod, requestPath);
        return ACTION_MAP.get(request);
    }


}
