package com.ivw.task.param;

import lombok.Data;

@Data
public class DefaultDbCollectParam {

    /**
     * 当前页
     */
    private Integer page;

    /**
     * 偏移量
     */
    private Integer offset;

    /**
     * 增量开始 id
     */
    private Long incrementId;
}
