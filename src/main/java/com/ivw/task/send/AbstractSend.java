package com.ivw.task.send;

import com.ivw.task.properties.AbstractSendProperties;
import lombok.Data;

/**
 * @author Yi
 */
@Data
public abstract class AbstractSend<T extends AbstractSendProperties> implements Message{

    T properties;
}
