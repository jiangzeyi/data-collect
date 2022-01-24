package com.ivw.task.handler;

import com.ivw.task.properties.GroovyHandlerProperties;
import lombok.Data;

import java.io.FileNotFoundException;

/**
 * @author Yi
 */
@Data
public abstract class AbstractHandler<T> implements Handler {

    protected T properties;

    @Override
    public Object handler(Object data) {
        startBefore();
        Object result = execute(data);
        finishAfter();
        return result;
    }

    abstract void startBefore();

    abstract Object execute(Object data);

    abstract void finishAfter();
}
