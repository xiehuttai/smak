package com.sanmo.smak.orm;

import com.sanmo.smak.annotation.transaction.Transaction;
import com.sanmo.smak.aop.Proxy;
import com.sanmo.smak.aop.ProxyChain;
import com.sanmo.smak.mvc.pojo.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

public class TransactionProxy implements Proxy {

    private static final Logger logger = LoggerFactory.getLogger(TransactionProxy.class);

    private static final ThreadLocal<Boolean> FLAG_HOLDER = new ThreadLocal<>(){
        @Override
        protected Boolean initialValue() {
            return false;
        }
    };

    @Override
    public Object doProxy(ProxyChain proxyChain) {
        Object result = null;
        Boolean flag = FLAG_HOLDER.get();
        Method targetMethod = proxyChain.getTargetMethod();
        if (!flag&&targetMethod.isAnnotationPresent(Transaction.class)){
            FLAG_HOLDER.set(true);
            DatabaseHelper.beginTransaction();
            try {
                DatabaseHelper.beginTransaction();
                logger.info("begin transaction ....");
                proxyChain.doProxyChain();
                logger.info("do sql ....");
                DatabaseHelper.commitTransaction();
                logger.info("commit transaction ....");
            } catch (Throwable throwable) {
                DatabaseHelper.rollbackTransaction();
                logger.info("rollback transaction ....");
                throwable.printStackTrace();
            }
        }else {
            try {
                result=proxyChain.doProxyChain();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
        return result;
    }
}
