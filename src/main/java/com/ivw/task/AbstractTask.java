package com.ivw.task;

import com.ivw.task.properties.TaskProperties;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.PersistJobDataAfterExecution;
import org.springframework.jdbc.core.JdbcTemplate;


/**
 * @author Yi
 */
@PersistJobDataAfterExecution
public abstract class AbstractTask implements Job {

    TaskProperties properties;

    static final String UPDATE_COLLECT_PARAM_SQL = "update DP_SYS_TASK set COLLECT_PARAM = '{collectParam}' where id = {id}";

    @Override
    public void execute(JobExecutionContext context) {
        try {
            startBefore(context);
            run(context);
        } catch (Exception e) {
            exceptionHandler(context,e);
        } finally {
            finishAfter(context);
        }
    }

    abstract void run(JobExecutionContext context);

    /**
     * 启动之前
     */
    abstract void startBefore(JobExecutionContext context);

    /**
     * 完成之后
     */
    abstract void finishAfter(JobExecutionContext context);

    /**
     * 异常处理
     */
    abstract void exceptionHandler(JobExecutionContext context, Exception e);
}
