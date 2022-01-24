package com.ivw.task.utils;

import org.quartz.JobKey;
import org.quartz.TriggerKey;

public class TaskUtils {

    private final static String TASK_NAME_PREFIX = "TASK_";

    private final static String DEFALUT_GROUP = "GROUP1";

    /**
     * 获取jobKey
     */
    public static JobKey getJobKey(String taskName) {
        return JobKey.jobKey(TASK_NAME_PREFIX + taskName, DEFALUT_GROUP);
    }

    public static JobKey getJobKey(String taskName,String groupName) {
        return JobKey.jobKey(TASK_NAME_PREFIX + taskName,groupName);
    }

    /**
     * 获取触发器key
     */
    public static TriggerKey getTriggerKey(String taskName) {
        return TriggerKey.triggerKey(TASK_NAME_PREFIX + taskName, DEFALUT_GROUP);
    }

    /**
     * 获取触发器key
     */
    public static TriggerKey getTriggerKey(String taskName,String group) {
        return TriggerKey.triggerKey(TASK_NAME_PREFIX + taskName, group);
    }
}
