package com.sanmo.smak.example.aspect;

import com.sanmo.smak.annotation.Controller;
import com.sanmo.smak.annotation.aop.Aspect;
import com.sanmo.smak.aop.AspectProxy;

import java.lang.reflect.Method;

@Aspect(Controller.class)
public class ControllerAspect extends AspectProxy {

    @Override
    public void before(Class<?> cls, Method method, Object[] params) {
        System.out.println("before");
    }

    @Override
    public void end(Class<?> cls, Method method, Object[] params) {
        System.out.println("end");
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
