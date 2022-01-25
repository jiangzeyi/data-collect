package com.ivw.task.properties;

import com.ivw.task.enums.DBType;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;


/**
 * @author Yi
 */
@Data
@NoArgsConstructor
public class DbIncrementCollectProperties extends AbstractDbCollectProperties {

    /**
     * 是否启用增量采集
     */
    private Boolean isIncrement;

    /**
     * 增量采集 key
     */
    private String primaryKey;

    /**
     * 增量开始 id
     */
    private Long incrementId;

    /**
     * 数据库类型
     */
    private DBType type;

}
