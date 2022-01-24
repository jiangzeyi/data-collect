package com.ivw.task.collect;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.ivw.task.enums.DBType;
import com.ivw.task.properties.DbIncrementCollectProperties;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.*;

/**
 * @author Yi
 * 增量同步
 */
public class DbDefaultCollect extends DbAbstractCollect<DbIncrementCollectProperties> {

    public DbDefaultCollect(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    Map<String, Object> buildQueryParam() {
        Map<String, Object> pageParam = new HashMap<>();
        pageParam.put("offset", properties.getOffset());
        pageParam.put("page", properties.getPage());
        pageParam.put("incrementId", properties.getIncrementId());
        return  pageParam;
    }

    @Override
    void startBefore() {
        if (Objects.isNull(properties)) {
            throw new RuntimeException("properties 不能为 Null");
        }
        // 是否启用分页查询
        if (properties.getIsPage()) {
            if (properties.getSql().lastIndexOf("{offset}") == -1 ||
                    properties.getSql().lastIndexOf("{page}") == -1) {
                throw new RuntimeException("请检查 SQL 是否包含 {offset} 和 {page} 参数");
            }
        }
        // 是否启用增量查询
        if (properties.getIsIncrement()) {
            if (properties.getSql().lastIndexOf("{incrementId}") == -1 ||
                    Objects.isNull(properties.getIncrementId())) {
                throw new RuntimeException("请检查 SQL 是否包含 {incrementId} 参数");
            }
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
        while (size > 0) {
            if (DBType.MYSQL.toString().equals(properties.getType().toString())) {
                pageParam.put("page", (Integer) pageParam.get("page") + properties.getOffset());
            } else if (DBType.ORACLE.toString().equals(properties.getType().toString())) {
                pageParam.put("page", (Integer) pageParam.get("page") + properties.getOffset());
                pageParam.put("offset", (Integer) pageParam.get("offset") + properties.getOffset());
            }
            logger.info("查询 Sql => {}", properties.getSql());
            logger.info("查询参数 => {}", pageParam);
            List<JSONObject> query = jdbcTemplate.query(StrUtil.format(properties.getSql(),pageParam), convertJson);
            size = query.size();
            data.addAll(query);
        }

        // 启用增量只记录最后 ID
        if (properties.getIsIncrement()) {
            // 记录增量 Id
            long maxPrimaryKey = data.stream().mapToLong(item -> item.getLong(properties.getPrimaryKey()))
                    .max().orElse(properties.getIncrementId());
            properties.setIncrementId(maxPrimaryKey);
        } else {
            properties.setPage((Integer) pageParam.get("page"));
            properties.setOffset((Integer) pageParam.get("offset"));
        }
        // 数据库同步配置
        this.setProperties(properties);
        return data;
    }

    @Override
    void finishAfter() {
        // 持久化数据库采集配置
    }
}
