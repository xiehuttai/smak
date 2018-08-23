package com.sanmo.smak.example.aspect;

import com.sanmo.smak.annotation.Controller;
import com.sanmo.smak.annotation.aop.Aspect;
import com.sanmo.smak.aop.AspectProxy;

import java.lang.reflect.Method;

@Aspect(Controller.class)
public class ControllerAspect extends AspectProxy {

    private long begin=0L;

    @Override
    public void before(Class<?> cls, Method method, Object[] params) {
        begin = System.currentTimeMillis();
        System.out.println("before");
    }

    @Override
    public void end(Class<?> cls, Method method, Object[] params) {
        System.out.println("end");
        System.out.println("invoke duration : "+(System.currentTimeMillis()-begin)+ " ms");
    }

    @Override
    public void after(Class<?> cls, Method method, Object[] params) {
        System.out.println("after");
    }

    @Override
    public void error(Class<?> cls, Method method, Object[] params) {
        System.out.println("error");
    }

    @Override
    public boolean intercept(Class<?> cls, Method method, Object[] params) {
        return true;
    }

    @Override
    public void begin() {
        System.out.println("before all");
    }
}
