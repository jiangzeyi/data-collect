package com.ivw.task.param;

import lombok.Data;

@Data
public class DbIncrementCollectParam extends DefaultDbCollectParam{

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
}
