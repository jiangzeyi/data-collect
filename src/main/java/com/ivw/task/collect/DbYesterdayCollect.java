package com.ivw.task.collect;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.ivw.task.enums.DBType;
import com.ivw.task.properties.DbBetweenCollectProperties;
import com.ivw.task.properties.DbIncrementCollectProperties;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.*;

/**
 * @author Yi
 * 同步昨天的数据
 */
public class DbYesterdayCollect  extends DbAbstractCollect<DbBetweenCollectProperties>  {


    public DbYesterdayCollect(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    Map<String, Object> buildQueryParam() {
        Map<String, Object> pageParam = new HashMap<>();
        pageParam.put("beginDate", properties.getBeginDate());
        pageParam.put("endDate", properties.getEndDate());
        pageParam.put("offset", properties.getOffset());
        pageParam.put("page", properties.getPage());
        return pageParam;
    }

    @Override
    void startBefore() {
        if (Objects.isNull(properties)) {
            throw new RuntimeException("properties 不能为 Null");
        }
        if (properties.getSql().lastIndexOf("{beginDate}") == -1 && properties.getSql().lastIndexOf("{endDate}") == -1) {
            throw new RuntimeException("请检查 SQL 语句是否包含 {beginDate} 和 {endDate} 参数");
        }
    }

    @Override
    Object run() {
        // 构造SQL查询参数
        Map<String, Object> pageParam = buildQueryParam();
        logger.info("查询 Sql => {}", properties.getSql());
        logger.info("查询参数 => {}", pageParam);
        // 生成 SQL
        List<JSONObject> data = new ArrayList<>(jdbcTemplate.query(StrUtil.format(properties.getSql(), pageParam), convertJson));

        int size = data.size();
        if (properties.getIsPage()) {
            while (size > 0) {
                if (DBType.MYSQL.toString().equals(properties.getDbType().toString())) {
                    pageParam.put("page", (Integer) pageParam.get("page") + properties.getOffset());
                } else if (DBType.ORACLE.toString().equals(properties.getDbType().toString())) {
                    pageParam.put("page", (Integer) pageParam.get("page") + properties.getOffset());
                    pageParam.put("offset", (Integer) pageParam.get("offset") + properties.getOffset());
                }
                logger.info("查询 Sql => {}", properties.getSql());
                logger.info("查询参数 => {}", pageParam);
                List<JSONObject> query = jdbcTemplate.query(StrUtil.format(properties.getSql(),pageParam), convertJson);
                size = query.size();
                data.addAll(query);
            }
        }

        logger.debug("data：{}",data);
        // 数据库同步配置
        this.setProperties(properties);
        return data;
    }

    @Override
    void finishAfter() {

    }
}
