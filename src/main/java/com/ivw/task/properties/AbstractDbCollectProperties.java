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
public abstract class AbstractDbCollectProperties<T extends DefaultDbCollectParam> extends AbstractCollectProperties {

    @NonNull
    private String sql;

    private DBType dbType;

    private T param;
}
