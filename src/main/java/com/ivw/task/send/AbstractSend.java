package com.ivw.task.send;

import com.ivw.task.properties.AbstractSendProperties;

/**
 * @author Yi
 */
public abstract class AbstractSend<T extends AbstractSendProperties> implements Message{

    T properties;
}
