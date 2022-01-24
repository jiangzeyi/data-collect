package com.ivw.task.properties;

import com.ivw.task.enums.DBType;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * @author Yi
 */
@Data
@NoArgsConstructor
public abstract class AbstractDbCollectProperties extends AbstractCollectProperties {

    @NonNull
    private String sql;

    @NonNull
    private String strategy;

    private Boolean isPage;

    private Integer page;

    private Integer offset;

    private DBType dbType;
}
