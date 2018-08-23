package com.sanmo.smak.aop;

import com.sanmo.smak.annotation.Service;
import com.sanmo.smak.annotation.aop.Aspect;
import com.sanmo.smak.annotation.transaction.Transaction;
import com.sanmo.smak.ioc.BeanHelper;
import com.sanmo.smak.ioc.ClassHelper;
import com.sanmo.smak.orm.TransactionProxy;

import java.lang.annotation.Annotation;
import java.util.*;

public final class AopHelper {

    static {
        try{
            Map<Class<?>, Set<Class<?>>> proxyMap = createProxyMap();
            Map<Class<?>, List<Proxy>> targetMap = createTargetMap(proxyMap);
            for (Map.Entry<Class<?>,List<Proxy>> targetEntry: targetMap.entrySet()){
                Class<?> targetClass = targetEntry.getKey();
                List<Proxy> proxyList = targetEntry.getValue();
                Object proxy = ProxyManager.creatProxy(targetClass, proxyList);
                BeanHelper.setBean(targetClass,proxy);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    /**
     * 获取切面所有对应的类
     * @param aspect
     * @return
     */
    private static Set<Class<?>> createTargetClassSet(Aspect aspect){
        Set<Class<?>> targetClassSet = new HashSet<>();
        Class<? extends Annotation> annotation = aspect.value();
        if (annotation!=null && !annotation.equals(Aspect.class)){
            targetClassSet.addAll(ClassHelper.getClassSetByAnnotation(annotation));
        }
        return targetClassSet;
    }


    /**
     * 生成切面对应映射关系
     * @return
     */
    private static Map<Class<?>,Set<Class<?>>> createProxyMap(){
        Map<Class<?>, Set<Class<?>>> proxyMap = new HashMap<>();
        addAspectProxy(proxyMap);
        addTransactionProxy(proxyMap);
        return proxyMap;
    }

    public static void addAspectProxy(Map<Class<?>, Set<Class<?>>> proxyMap){
        Set<Class<?>> proxyClassSet = ClassHelper.getClassSetBySuper((AspectProxy.class));
        for (Class<?> proxyClass: proxyClassSet){
            if(proxyClass.isAnnotationPresent(Aspect.class)){
                Aspect aspect = proxyClass.getAnnotation(Aspect.class);
                Set<Class<?>> targetClassSet = createTargetClassSet(aspect);
                proxyMap.put(proxyClass,targetClassSet);
            }
        }
    }

    public static void addTransactionProxy(Map<Class<?>, Set<Class<?>>> proxyMap){
        Set<Class<?>> classSetByAnnotation = ClassHelper.getClassSetByAnnotation(Service.class);
        proxyMap.put(TransactionProxy.class,classSetByAnnotation);
    }

    /**
     * 根据切面关系生成目标类和代理类的映射关系
     * @param proxyMap
     * @return
     */
    private static Map<Class<?> ,List<Proxy>> createTargetMap(Map<Class<?>,Set<Class<?>>> proxyMap){
        Map<Class<?>, List<Proxy>> targetMap = new HashMap<>();
        for (Map.Entry<Class<?>,Set<Class<?>>> proxyEntry: proxyMap.entrySet()){
            Class<?> proxyClass = proxyEntry.getKey();
            Set<Class<?>> targetClassSet = proxyEntry.getValue();
            for (Class<?> targetClass:targetClassSet){
                Proxy proxy = null;
                try {
                    proxy = (Proxy)proxyClass.newInstance();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                if (targetMap.containsKey(targetClass)){
                    targetMap.get(targetClass).add(proxy);
                }else {
                    ArrayList<Proxy> proxyList = new ArrayList<>();
                    proxyList.add(proxy);
                    targetMap.put(targetClass,proxyList);
                }
            }

        }
        return targetMap;
    }




}
