package com.xuyg.tinyframework.util;

import com.sun.org.apache.regexp.internal.RE;
import com.xuyg.tinyframework.annotation.Action;
import com.xuyg.tinyframework.bean.Handler;
import com.xuyg.tinyframework.bean.Request;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by xyg on 2017/3/6.
 */
public final class ControllerUtil {
    private static final Map<Request,Handler> ACTION_MAP=new HashMap<Request, Handler>();

    static {
        Set<Class<?>> controllerSet=FrameworkClassUtil.getControllerClassSet();
        if(!controllerSet.isEmpty()) {
            for (Class<?> cls : controllerSet) {
                Method[] methods = cls.getDeclaredMethods();
                if (ArrayUtils.isEmpty(methods)) continue;
                for (Method method : methods) {
                    if (!method.isAnnotationPresent(Action.class)) continue;
                    Action action = method.getAnnotation(Action.class);
                    String mapping = action.value();
                    if (!mapping.matches("\\w+:/\\w*")) continue;
                    String[] array = mapping.split(":");
                    if (ArrayUtils.isNotEmpty(array) && array.length == 2) {
                        String requestMethod = array[0];
                        String requestPath = array[1];
                        Request request = new Request(requestMethod, requestPath);
                        Handler handler = new Handler(cls, method);
                        ACTION_MAP.put(request, handler);
                    }

                }
            }
        }
    }

    public static Handler getHandler(String reqMethod,String reqPath){
        Request req=new Request(reqMethod,reqPath);
        return ACTION_MAP.get(req);
    }

}
