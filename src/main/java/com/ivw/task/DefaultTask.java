package com.ivw.task;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.ivw.task.properties.TaskProperties;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Yi
 */
public class DefaultTask extends AbstractTask {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    void run(JobExecutionContext context) {
        // 执行采集数据
        Object sourceData = properties.getCollect().collect();
        // 处理数据
        Object handlerData = properties.getHandler().handler(sourceData);
        // 发送数据
        Map<String, Object> sendData = new HashMap<>();
        sendData.put("sourceData", sourceData);
        sendData.put("handlerData", handlerData);
        properties.getSend().send(sendData);

    }

    @Override
    void startBefore(JobExecutionContext context) {
        // 获取任务配置
        logger.info("初始化任务配置");
        JobDataMap mergedJobDataMap = context.getMergedJobDataMap();
        properties = (TaskProperties)mergedJobDataMap.get(TaskManager.TASK_PARAM_KEY);
        properties.setStartTime(System.currentTimeMillis());
        logger.info("开始执行任务");
    }

    @Override
    void finishAfter(JobExecutionContext context) {
        // 可以执行数据库保存操作等的
        logger.info("任务执行完毕，任务名称：" + properties.getName() + "  总共耗时：" + (System.currentTimeMillis() - properties.getStartTime()) + "毫秒");
        // 更新相关配置
        /*if (Objects.isNull(properties.getJdbcTemplate())) {
            return;
        }
        if(Objects.isNull(properties.getCollect().getProperties())) {
            return;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("collectParam",JSONObject.toJSONString(properties.getCollect().getProperties()));
        map.put("id",properties.getId());
        String updateSql = StrUtil.format(UPDATE_COLLECT_PARAM_SQL,map);
        properties.getJdbcTemplate().update(updateSql);*/
    }

    @Override
    void exceptionHandler(JobExecutionContext context, Exception e) {
        logger.info("异常堆栈信息：{}",e.getMessage());
    }
}
