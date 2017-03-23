package com.xuyg.tinyframework.annotation;

import java.lang.annotation.*;

/**
 * Created by XYG on 2017-03-23.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Aspect {
    Class<? extends Annotation> value();
}
