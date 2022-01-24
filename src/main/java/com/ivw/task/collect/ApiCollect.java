package com.ivw.task.collect;

import com.ivw.task.properties.AbstractCollectProperties;
import com.ivw.task.properties.ApiCollectProperties;
import org.springframework.web.client.RestTemplate;

/**
 * @author Yi
 */
public class ApiCollect extends AbstractCollect<ApiCollectProperties>{

    private final RestTemplate restTemplate;

    public ApiCollect(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    @Override
    void startBefore() {

    }

    @Override
    Object run() {
        if ("GET".equals(properties.getMethod())) {

        } else {
        }
        return null;
    }

    @Override
    void finishAfter() {

    }


}
