package com.xuyg.tinyframework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by xyg on 2017/3/2.
 */
public final class ReflectionUtil {
    private  static final Logger logger= LoggerFactory.getLogger(ReflectionUtil.class);

    public  static Object newInstance(Class<?> cls){
        Object instance;
        try {
            instance=cls.newInstance();
        }catch (Exception e){
            logger.error("new instance failed",e);
            throw new RuntimeException(e);
        }
        return  instance;
    }

    public static  Object invokeMethod(Object obj, Method method,Object... arg){
        Object result;
        try{
            method.setAccessible(true);
            result=method.invoke(obj,arg);
        }
        catch (Exception e){
            logger.error("invoke method failed",e);
            throw new RuntimeException(e);
        }
        return  result;
    }

    public  static  void  setField(Object obj, Field field,Object value){
        try {
            field.setAccessible(true);;
            field.set(obj,value);
        }
        catch (Exception e){
            logger.error("set field failed",e);
            throw new RuntimeException(e);
        }
    }

}
