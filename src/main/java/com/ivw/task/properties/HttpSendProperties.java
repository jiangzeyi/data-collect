package com.ivw.task.properties;

import lombok.Builder;
import lombok.Data;

/**
 * @author Yi
 */
@Data
@Builder
public class HttpSendProperties extends AbstractSendProperties {
    private String url;
}
