package com.xuyg.tinyframework.proxy;

/**
 * Created by XYG on 2017-03-23.
 */
public interface Proxy {
    Object doProxy(ProxyChain proxyChain) throws  Throwable;
}
