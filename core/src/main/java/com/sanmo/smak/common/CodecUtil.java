package com.sanmo.smak.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;


/*转换url中的特殊字符*/
public class CodecUtil {

    private static final Logger logger = LoggerFactory.getLogger(CodecUtil.class);

    public static String encodeUrl(String source){
        String target=null;
        try {
            target=URLEncoder.encode(source,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return target;
    }


    public static String decodeUrl(String source)  {
        String target=null;
        try {
            target=URLDecoder.decode(source,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return target;
    }


}
