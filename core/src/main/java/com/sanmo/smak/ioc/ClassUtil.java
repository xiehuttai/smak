package com.sanmo.smak.ioc;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;


/* 2 加载clazz的工具类 */
public class ClassUtil {

    private static final Logger logger = LoggerFactory.getLogger(ClassUtil.class);

    public static ClassLoader getClassLoader(){
        return Thread.currentThread().getContextClassLoader();
    }

    /* isInitlized 标识是否执行类的静态代码块，标记为false可提高类加载速度*/
    public static Class<?> loadClass(String className,boolean isInitlized ){
        Class<?> cls;
        try {
            cls=Class.forName(className,isInitlized,getClassLoader());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return cls;
    }

    public static Class<?> loadClass(String className ){
        Class<?> cls;
        try {
            /**
             * Class.forName 选择 执行静态代码块
             */
            cls=Class.forName(className,true,getClassLoader());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return cls;
    }

    public static Set<Class<?>> getClassSet(String packageName){
        Set<Class<?>> classes = new HashSet<>();
        try {
            Enumeration<URL> urls = getClassLoader().getResources(packageName.replace(".", "/"));
            while (urls.hasMoreElements()){
                URL url = urls.nextElement();
                if (url!=null){
                    String protocol = url.getProtocol();
                    if (protocol.equals("file")){
                        String packagePath = url.getPath().replaceAll("%20", " ");
                        addClass(classes,packagePath,packageName);
                    }else if (protocol.equals("jar")){
                        JarURLConnection jarURLConnection = (JarURLConnection)url.openConnection();
                        if (jarURLConnection!=null){
                            JarFile jarFile = jarURLConnection.getJarFile();
                            if (jarFile!=null){
                                Enumeration<JarEntry> entries = jarFile.entries();
                                while(entries.hasMoreElements()){
                                    JarEntry jarEntry = entries.nextElement();
                                    String jarEntryName = jarEntry.getName();
                                    if (jarEntryName.endsWith(".class")){
                                        String className = jarEntryName.substring(0, jarEntryName.lastIndexOf(".")).replaceAll("/", ".");
                                        doAddClass(classes,className);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return classes;
    }

    private static void doAddClass(Set<Class<?>> classes, String className) {
        Class<?> cls = loadClass(className, false);
        classes.add(cls);
    }

    private static void addClass(Set<Class<?>> classes, String packagePath, String packageName) {

        File[] files = new File(packagePath).listFiles(
                (f) -> f.isFile() && f.getName().endsWith(".class") || f.isDirectory() );

        for (File f: files) {
            String fileName = f.getName();
            if(f.isFile()){
                String className=fileName.substring(0,fileName.lastIndexOf("."));
                if (StringUtils.isNotEmpty(packageName)){
                    className=packageName+"."+className;
                }
                doAddClass(classes,className);
            }else{
                String subPackagePath=fileName;
                if (StringUtils.isNotEmpty(packagePath)){
                    subPackagePath=packagePath+"/"+subPackagePath;
                }
                String subPackageName = fileName;
                if (StringUtils.isNotEmpty(packageName)){
                    subPackageName=packageName+"."+subPackageName;
                }
                addClass(classes,subPackagePath,subPackageName);
            }
        }
    }

}
