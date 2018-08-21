package com.sanmo.smak.mvc.controller;

import com.sanmo.smak.annotation.Action;
import com.sanmo.smak.ioc.ClassHelper;
import com.sanmo.smak.mvc.pojo.Request;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/* 6 controller、action */
public class ControllerHelper {

    private static final Map<Request,Handler> ACTION_MAP= new HashMap<Request,Handler>();

    private static final Logger logger = LoggerFactory.getLogger(ControllerHelper.class);

    static {

        Set<Class<?>> controllerClassSet = ClassHelper.getControllerClassSet();
        logger.info("controllerClassSet:{},{}",controllerClassSet.size(),controllerClassSet);
        for (Class<?> cls : controllerClassSet){
            Method[] methods = cls.getMethods();
            logger.info("methods:{},{}",methods.length,methods);
            for(Method m: methods){
                if (m.isAnnotationPresent(Action.class)){
                    Action annotation = m.getAnnotation(Action.class);
                    String mapping = annotation.value();
                    logger.debug("annotation value:{}",mapping);
                    boolean matches = mapping.matches("\\w+:/\\w*");
                    logger.debug("matches:{}",matches);
                    if (matches){
                        String[] array = mapping.split(":");
                        logger.debug("array:{}",array);
                        if (ArrayUtils.isNotEmpty(array)&& array.length==2){
                            String requestMethod = array[0].toUpperCase();
                            String requestPath = array[1];
                            logger.debug("request action:{}",requestMethod,requestPath);
                            Request request = new Request(requestMethod, requestPath);
                            Handler handler = new Handler(cls, m);
                            logger.debug("action:{}",request,handler);
                            ACTION_MAP.put(request,handler);
                        }
                    }
                }
            }
        }
        logger.info("action_map:{}",ACTION_MAP);
    }


    public static Handler getHandler(String requestMethod,String requestPath){
        Request request = new Request(requestMethod, requestPath);
        return ACTION_MAP.get(request);
    }


}
