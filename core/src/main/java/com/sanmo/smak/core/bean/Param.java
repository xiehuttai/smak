package com.sanmo.smak.core.bean;

import com.sanmo.smak.common.CastUtil;

import java.util.Map;

public class Param {

    private Map<String,Object> paramMap;

    public Param(Map<String, Object> paramMap) {
        this.paramMap = paramMap;
    }

    public long getLong(String name){
        return CastUtil.getLong(paramMap.get(name));
    }

}
