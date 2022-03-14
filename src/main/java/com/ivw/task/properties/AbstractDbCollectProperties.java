package com.ivw.task.properties;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * @author Yi
 */
@Data
@NoArgsConstructor
public abstract class AbstractDbCollectProperties<T> extends AbstractCollectProperties {

    @NonNull
    private String sql;

    /**
     * 数据库类型
     */
    private Integer type;

    /**
     * 是否开启分月
     */
    private boolean isPage;

    /**
     * 是否开启增量同步
     */
    private boolean isIncrement;

    /**
     * 增量 Key
     */
    private String primaryKey;

    private T param;
}
