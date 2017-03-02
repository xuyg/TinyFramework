package com.xuyg.tinyframework.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileFilter;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Created by xyg on 2017/3/2.
 */
public final class ClassUtil {
    private static final Logger logger= LoggerFactory.getLogger(ClassUtil.class);

    private  static final Map<Class<?>,Object> MAP=new ConcurrentHashMap<Class<?>, Object>();

    public  static  ClassLoader getClassloader(){
        return Thread.currentThread().getContextClassLoader();
    }

    public  static Class<?> loadClass(String clsName,boolean isInit){
        Class<?> cls;
        try {
            cls=Class.forName(clsName,isInit,getClassloader());
        }
        catch (ClassNotFoundException e){
            logger .error("load class failed",e);
            throw  new RuntimeException( e);
        }
        return  cls;
    }

    public  static  Set<Class<?>> getClassSet(String pkgName){
        Set<Class<?>> set=new HashSet<Class<?>>();
        try {
            Enumeration<URL> urls=getClassloader().getResources(pkgName.replace('.','/'));
            while (urls.hasMoreElements()){
                URL url=urls.nextElement();
                if(url==null )continue;
                String protocol=url.getProtocol();
                if("file".equals(protocol)){
                    String pkgPath=url.getPath().replaceAll("%20"," ");
                    addClass(set,pkgPath,pkgName);
                }
                else if("jar".equals(protocol)){
                    JarURLConnection conn=(JarURLConnection)url.openConnection();
                    if(conn==null) continue;
                    JarFile jarFile =conn.getJarFile();
                    Enumeration<JarEntry> jarEntries=jarFile.entries();
                    while (jarEntries.hasMoreElements()){
                        JarEntry entry=jarEntries.nextElement();
                        String entryName=entry.getName();
                        if(entryName.endsWith(".class")){
                            String clsName=entryName.substring(0,entryName.lastIndexOf('.')).replaceAll("/",".");
                            addClass(set,clsName);
                        }
                    }
                }
            }

        }
        catch (Exception e){
            logger.error("get class ser failed",e);
        }
        return  set;
    }


    private   static  void  addClass(Set<Class<?>>set,String pkgPath,String pkgName){
        File[] files=new File(pkgPath).listFiles(new FileFilter() {
            public boolean accept(File f) {
                return (f.isFile()&&f.getName().endsWith(".class"))||f.isDirectory();
            }
        });
        for (File file:files){
            String name=file.getName();
            if(file.isFile()){
                String clsName=name.substring(0,name.lastIndexOf('.'));
                if(StringUtils.isNotEmpty(clsName)){
                    clsName=pkgName+"."+clsName;
                }
                addClass(set,clsName);
            }else{
                String subPkgPath=name;
                if(StringUtils.isNotEmpty(pkgPath)){
                    subPkgPath=pkgPath+"/"+subPkgPath;
                }
                String subPkgName=name;
                if(StringUtils.isNotEmpty(pkgName)){
                    subPkgName=pkgName+"."+subPkgName;
                }
                addClass(set,subPkgPath,subPkgName);
            }
        }

    }

    private  static  void addClass(Set<Class<?>> set,String clsName){
        Class<?> cls=loadClass(clsName,false);
        set.add(cls);
    }

}
