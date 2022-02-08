package com.ivw.task.send;

import cn.hutool.http.ContentType;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.ivw.task.properties.HttpSendProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Yi
 */
public class HttpSend extends AbstractSend<HttpSendProperties> {

    Logger logger = LoggerFactory.getLogger(HttpSend.class);

    @Override
    public void send(Object data) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("data", JSONObject.toJSONString(data));
        logger.info("请求参数：{}", properties);;
        if(properties.getProxyOpen()) {
            HttpRequest.post(properties.getUrl())
                    .setHttpProxy(properties.getProxyHost(),properties.getProxyPort())
                    .header("Content-Type", ContentType.JSON.getValue()).header("Content-Type", "application/x-www-form-urlencoded;charset=utf-8;")
                    .form(paramMap).execute().body();
        }
         else {
             HttpUtil.post(properties.getUrl(),paramMap);
        }
    }
}
