package com.xuyg.tinyframework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by xyg on 2017/3/2.
 */
public final class PropertiesUtil {

    private static final Logger logger = LoggerFactory.getLogger(PropertiesUtil.class);
    private static ConcurrentHashMap<String,Properties> properties=new ConcurrentHashMap<String, Properties>();

    public static Properties loadProperties(String filePath) {
        if(properties.containsKey(filePath))
            return properties.get(filePath);
        Properties prop = null;
        InputStream stream = null;
        try {
            stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath);
            if (stream == null) {
                throw new FileNotFoundException("not found " + filePath);
            }
            prop = new Properties();
            prop.load(stream);
            properties.put(filePath,prop);
        } catch (IOException e) {
            logger.error("load properties file failed", e);
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    logger.error("close input stream failed", e);
                }
            }
            return prop;
        }
    }

    public static String getValue(String filePath, String key) {
        return getValue(loadProperties(filePath), key);
    }

    public static String getValue(Properties props, String key) {
        String value = null;
        if (props != null && props.containsKey(key)) {
            value = props.getProperty(key);
        }
        return value;
    }
}
