package com.xuyg.tinyframework.bean;

import java.lang.reflect.Method;

/**
 * Created by XYG on 2017-03-02.
 */
public class Handler {
    private Class<?> controllerClass;
    private Method method;

    public Handler(Class<?> cls,Method method){
        this.controllerClass=cls;
        this.method=method;
    }

    public Method getMethod() {
        return method;
    }

    public Class<?> getControllerClass() {
        return controllerClass;
    }
}
