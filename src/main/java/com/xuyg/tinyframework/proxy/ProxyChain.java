package com.xuyg.tinyframework.proxy;

import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by XYG on 2017-03-23.
 */
public class ProxyChain {
    private final Class<?> targetCls;
    private final Object targetObj;
    private final Method targetMethod;
    private final MethodProxy methodProxy;
    private final Object[] methodParams;
    private List<Proxy> proxyList=new ArrayList<>();
    private int proxyIndex=0;

    public ProxyChain(Class<?> targetCls,Object targetObj,Method targetMethod,MethodProxy methodProxy,Object[] methodParams ,List<Proxy> proxyList){
        this.methodParams=methodParams;
        this.methodProxy=methodProxy;
        this.targetCls=targetCls;
        this.targetObj=targetObj;
        this.targetMethod=targetMethod;
        this.proxyList=proxyList;
    }

    public Class<?> getTargetCls() {
        return targetCls;
    }

    public  Object getTargetObj(){
        return  targetObj;
    }

    public Object[] getMethodParams() {
        return methodParams;
    }

    public Object doProxyChain() throws Throwable{
        Object result;
        if(proxyIndex<proxyList.size()){
            result=proxyList.get(proxyIndex++).doProxy(this);
        }
        else {
            result=methodProxy.invokeSuper(targetObj,methodParams);
        }
        return result;
    }
}
