package com.xuyg.tinyframework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by xyg on 2017/3/2.
 */
public final class StreamUtil {
    private  static final Logger logger= LoggerFactory.getLogger(StreamUtil.class);

    public  static  String getString(InputStream stream){
        StringBuilder sb=new StringBuilder();
        try{
            BufferedReader br=new BufferedReader(new InputStreamReader(stream));
            String line;
            while ((line=br.readLine())!=null){
                sb.append(line);
            }
        }
        catch (Exception e){
            logger.error("get string failed",e);
        }
        return  sb.toString();
    }
}
