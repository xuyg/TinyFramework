package com.xuyg.tinyframework.util;

import com.xuyg.tinyframework.annotation.Controller;

import java.util.Arrays;

/**
 * Created by xyg on 2017/3/6.
 */
public final class BootstrapUtil {
    public static void init(){
        Class<?>[] set=new Class[]{
                FrameworkClassUtil.class,
                BeanUtil.class,
                IocUtil.class,
                ControllerUtil.class
        };
        Arrays.stream(set).forEach(p->{
            ClassUtil.loadClass(p.getName(),false);
        });
    }
}
