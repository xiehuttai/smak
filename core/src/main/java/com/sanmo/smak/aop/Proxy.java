package com.sanmo.smak.aop;

public interface Proxy {

    /**
     * 执行链式调用
     */
    Object doProxy(ProxyChain proxyChain);

}
