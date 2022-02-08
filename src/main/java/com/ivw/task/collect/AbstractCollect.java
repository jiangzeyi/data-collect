package com.ivw.task.collect;

import com.ivw.task.properties.AbstractCollectProperties;
import com.ivw.task.sql.SqlSource;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Yi
 */
@Data
public abstract class AbstractCollect<T extends AbstractCollectProperties> implements Collect {

    T properties;

    SqlSource sqlSource;

    final Logger logger = LoggerFactory.getLogger(AbstractCollect.class);

    @Override
    public Object collect() {
        startBefore();
        Object data = run();
        finishAfter();
        return data;
    }


    abstract void startBefore();

    abstract Object run();

    abstract void finishAfter();

}
