package com.ivw.task.properties;

import com.ivw.task.enums.DBType;
import com.ivw.task.param.DefaultDbCollectParam;
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
    private DBType dbType;

    /**
     * 是否开启分月
     */
    private Boolean isPage;

    /**
     * 是否开启增量同步
     */
    private Boolean isIncrement;

    /**
     * 增量 Key
     */
    private String primaryKey;

    private T param;
}
