package com.cv4j.rxcache.extra.persistence.disk.converter;

import com.alibaba.fastjson.JSON;
import com.cv4j.rxcache.persistence.disk.converter.AbstractConverter;

import java.lang.reflect.Type;

/**
 * Created by tony on 2018/9/29.
 */
public class FastJSONConverter extends AbstractConverter {

    @Override
    protected <T> T fromJson(String json, Type type) {

        return JSON.parseObject(json, type);
    }

    @Override
    protected String toJson(Object data) {

        return JSON.toJSONString(data);
    }
}
