package com.ivw.task.send;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.ivw.task.properties.HttpSendProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Yi
 */
public class HttpSend extends AbstractSend<HttpSendProperties> {


    @Override
    public void send(Object data) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("data", JSONObject.toJSONString(data));
        HttpUtil.post(properties.getUrl(),paramMap);
    }
}
