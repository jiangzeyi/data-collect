package com.ivw.task.properties;

import lombok.Data;

/**
 * @author Yi
 */
@Data
public class ApiCollectProperties extends AbstractCollectProperties{

    private String url;

    private String method;

    private String params;

    private String authType;

    private String authParam;
}
