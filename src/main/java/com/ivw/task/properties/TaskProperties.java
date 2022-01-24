package com.ivw.task.properties;

import com.ivw.task.collect.AbstractCollect;
import com.ivw.task.collect.Collect;
import com.ivw.task.handler.AbstractHandler;
import com.ivw.task.handler.Handler;
import com.ivw.task.send.AbstractSend;
import com.ivw.task.send.Message;
import lombok.Builder;
import lombok.Data;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author Yi
 */
@Data
@Builder
public class TaskProperties {

    /**
     * 任务Id
     */
    private Long id;

    /**
     * 任务名称
     */
    private String name;

    /**
     * cron 表达式
     */
    private String cronExpression;

    /**
     * 任务状态
     */
    private Integer status;

    /**
     * 开始时间
     */
    private Long startTime;

    /**
     * 数据获取
     */
    private AbstractCollect collect;

    /**
     * 数据处理
     */
    private AbstractHandler handler;

    /**
     * 数据发送
     */
    private AbstractSend send;

    /**
     * 持久化任务
     */
    private JdbcTemplate jdbcTemplate;

}
