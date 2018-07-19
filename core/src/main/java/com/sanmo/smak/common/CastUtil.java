package com.sanmo.smak.common;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

public class CastUtil {
    public static long getLong(Object str) {
        return Long.parseLong((String)str);
    }

    public static void parseString(Map<String,Object> map,String str){
        String url = CodecUtil.decodeUrl(str);
        String[] urlParams = StringUtils.split(url, "&");
        if (ArrayUtils.isNotEmpty(urlParams))
            for (String param: urlParams){
                String[] paramkv = StringUtils.split(param, "=");
                if (ArrayUtils.isNotEmpty(paramkv)&& paramkv.length==2)
                    map.put(paramkv[0],paramkv[1]);
            }
    }

}
