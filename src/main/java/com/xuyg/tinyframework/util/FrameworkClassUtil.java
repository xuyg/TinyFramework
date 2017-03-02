package com.xuyg.tinyframework.util;

import com.xuyg.tinyframework.annotation.Inject;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * Created by xyg on 2017/3/2.
 */
public final class FrameworkClassUtil {
    static {
        //实现IOC
        Map<Class<?>,Object> map=BeanUtil.getBeanMap();
        if(!map.isEmpty()){
            for (Map.Entry<Class<?>,Object> item:map.entrySet()) {
                Class<?> cls = item.getKey();
                Object instance = item.getValue();
                Field[] fields=cls.getDeclaredFields();
                if(ArrayUtils.isNotEmpty(fields)){
                    for (Field field:fields){
                        if(field.isAnnotationPresent(Inject.class)){
                            Class<?> fieldCls=field.getType();
                            Object fieldInstance=map.get(fieldCls);
                            if(fieldInstance!=null){
                                ReflectionUtil.setField(instance,field,fieldInstance);
                            }
                        }
                    }
                }
            }
        }
    }
    private static final Logger logger= LoggerFactory.getLogger(FrameworkClassUtil.class);
    


}
