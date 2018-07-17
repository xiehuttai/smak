package com.sanmo.smak.framework.example.dao.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {

    private static final Logger LOG=LoggerFactory.getLogger(PropertiesUtil.class);

    public static Properties loadProps(String fileName)  {
        Properties props=new Properties();
        InputStream is = null;
        is = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
        if (is==null)
            try {
                throw new FileNotFoundException(fileName + "file is not found.");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        try {
            props.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (is!=null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return props;
    }
}
