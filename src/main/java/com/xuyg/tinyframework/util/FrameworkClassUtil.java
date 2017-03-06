package com.xuyg.tinyframework.util;

import com.xuyg.tinyframework.annotation.Controller;
import com.xuyg.tinyframework.annotation.Inject;
import com.xuyg.tinyframework.annotation.Service;
import com.xuyg.tinyframework.constant.IFrameworkProperties;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by xyg on 2017/3/2.
 */
public final class FrameworkClassUtil {

    static {
        String basePkg=PropertiesUtil.getValue(IFrameworkProperties.File,IFrameworkProperties.APP_BASE_PACKAGE);
        CLASSES_SET=ClassUtil.getClassSet(basePkg);
    }
    private static final Logger logger= LoggerFactory.getLogger(FrameworkClassUtil.class);
    private  static  final Set<Class<?>> CLASSES_SET;

    public static Set<Class<?>> getClassSet(){
        return CLASSES_SET;
    }

    public static  Set<Class<?>> getControllerClassSet() {
        Set<Class<?>> set = new HashSet<Class<?>>();
        CLASSES_SET.forEach(p -> {
            if (p.isAnnotationPresent(Controller.class))
                set.add(p);
        });
        return set;
    }

    public  static  Set<Class<?>> getServiceSet(){
        Set<Class<?>> set=new HashSet<Class<?>>();
        CLASSES_SET.forEach(p -> {
            if (p.isAnnotationPresent(Service.class))
                set.add(p);
        });
        return set;
    }

    public  static  Set<Class<?>> getBeanClassSet(){
        Set<Class<?>> set=new HashSet<Class<?>>();
        set.addAll(getServiceSet());
        set.addAll(getControllerClassSet());
        return set;
    }

}
