package com.xuyg.tinyframework.util;

import com.xuyg.tinyframework.constant.IFrameworkProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by xyg on 2017/3/2.
 */
public final class BeanUtil {
    private static final Logger logger= LoggerFactory.getLogger(BeanUtil.class);
    private  static  final Map<Class<?>,Object> MAP=new HashMap<Class<?>, Object>();

    static {
        Set<Class<?>> set=ClassUtil.getClassSet(PropertiesUtil.getValue(IFrameworkProperties.File,IFrameworkProperties.APP_BASE_PACKAGE));
        for (Class<?> cls:set){
            Object obj=ReflectionUtil.newInstance(cls);
            MAP.put(cls,obj);
        }
    }

    public  static  Map<Class<?>,Object> getBeanMap(){
        return  MAP;
    }

    public  static  Object getBean(Class<?> cls){
        if(!MAP.containsKey(cls)){
            throw  new RuntimeException("class not found:"+cls);
        }
        return  MAP.get(cls);
    }
}
