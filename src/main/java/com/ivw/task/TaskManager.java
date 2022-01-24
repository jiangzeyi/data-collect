package com.ivw.task;

import com.ivw.task.enums.TaskStatus;
import com.ivw.task.properties.TaskProperties;
import com.ivw.task.utils.TaskUtils;
import org.quartz.*;

/**
 * @author Yi
 */
public class TaskManager {

    private final Scheduler scheduler;

    public static final String TASK_PARAM_KEY = "TASK_PARAM_KEY";

    public TaskManager(Scheduler scheduler) {
        this.scheduler = scheduler;
    }



    /**
     * 添加任务
     */
    public void addTask(TaskProperties properties) {

        try {
            //构建job信息
            JobDetail jobDetail = JobBuilder.newJob(DefaultTask.class).withIdentity(TaskUtils.getJobKey(properties.getName())).build();

            //表达式调度构建器
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(properties.getCronExpression())
                    .withMisfireHandlingInstructionDoNothing();

            //按新的cronExpression表达式构建一个新的trigger
            CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(TaskUtils.getTriggerKey(properties.getName())).withSchedule(scheduleBuilder).build();

            //放入参数，运行时的方法可以获取
            jobDetail.getJobDataMap().put(TASK_PARAM_KEY, properties);

            scheduler.scheduleJob(jobDetail, trigger);

            if(properties.getStatus() == TaskStatus.PAUSE.getValue()){
                pauseJob(properties.getName());
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    public void updateTask(TaskProperties properties) {
        try {
            TriggerKey triggerKey = TaskUtils.getTriggerKey(properties.getName());

            //表达式调度构建器
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(properties.getCronExpression())
                    .withMisfireHandlingInstructionDoNothing();

            CronTrigger trigger = getCronTrigger(properties.getName());

            //按新的cronExpression表达式重新构建trigger
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();

            //参数
            trigger.getJobDataMap().put(TASK_PARAM_KEY, properties);

            scheduler.rescheduleJob(triggerKey, trigger);

            //暂停任务
            if(properties.getStatus() == TaskStatus.PAUSE.getValue()){
                pauseJob(properties.getName());
            }

        } catch (SchedulerException e) {
            throw new RuntimeException("更新定时任务失败", e);
        }

    }

    public static void run(Scheduler scheduler, TaskProperties properties) {
        try {
            //参数
            JobDataMap dataMap = new JobDataMap();
            dataMap.put(TASK_PARAM_KEY, properties);

            scheduler.triggerJob(TaskUtils.getJobKey(properties.getName()), dataMap);
        } catch (SchedulerException e) {
            throw new RuntimeException("立即执行定时任务失败", e);
        }
    }

    /**
     * 暂停任务
     */
    public void pauseJob(String taskName) {
        try {
            scheduler.pauseJob(TaskUtils.getJobKey(taskName));
        } catch (SchedulerException e) {
            throw new RuntimeException("暂停定时任务失败", e);
        }
    }

    /**
     * 恢复任务
     */
    public void resumeJob(String taskName) {
        try {
            scheduler.resumeJob(TaskUtils.getJobKey(taskName));
        } catch (SchedulerException e) {
            throw new RuntimeException("暂停定时任务失败", e);
        }
    }

    /**
     * 获取表达式触发器
     */
    public CronTrigger getCronTrigger(String taskName) {
        try {
            return (CronTrigger) scheduler.getTrigger(TaskUtils.getTriggerKey(taskName));
        } catch (SchedulerException e) {
            throw new RuntimeException("获取定时任务CronTrigger出现异常", e);
        }
    }

    /**
     * 删除定时任务
     */
    public void deleteScheduleJob(String taskName) {
        try {
            scheduler.deleteJob(TaskUtils.getJobKey(taskName));
        } catch (SchedulerException e) {
            throw new RuntimeException("删除定时任务失败", e);
        }
    }

    public static void main(String[] args) {
        // mvn install:install-file -Dfile=ivw-task-1.0.jar -DgroupId=com.ivw -DartifactId=ivw-task -Dversion=1.0.0 -Dpackaging=jar
    }
}
