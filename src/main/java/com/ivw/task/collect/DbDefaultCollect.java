package com.ivw.task.collect;

import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.ivw.task.enums.DBType;
import com.ivw.task.properties.DbIncrementCollectProperties;
import com.ivw.task.sql.SqlSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;

import java.sql.Connection;
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
    Object[] buildArgs(List<String> parameterMappings) {
        Object[] args = new Object[parameterMappings.size()];
        for (int i = 0; i < parameterMappings.size(); i++) {
            args[i] = ReflectUtil.getFieldValue(properties.getParam(), parameterMappings.get(i));
        }
        return args;
    }


    @Override
    void startBefore() {
        if (Objects.isNull(properties)) {
            throw new RuntimeException("properties 不能为 Null");
        }
        // 解析 SQL
        SqlSource sqlSource = new SqlSource("#{", "}", new ArrayList<>());
        sqlSource.parse(properties.getSql());
        super.setSqlSource(sqlSource);
    }

    @Override
    Object run() {
        // 构造SQL查询参数
        logger.info("查询 Sql => {}", properties.getSql());
        logger.info("查询参数 => {}", properties.getParam());
        List<String> parameterMappings = sqlSource.getParameterMappings();
        if (parameterMappings == null || parameterMappings.isEmpty()) {
            return (jdbcTemplate.query(sqlSource.getSql(), convertJson));
        }
        List<JSONObject> dataAll = new ArrayList<>();

        if (properties.getIsPage()) {
            int size;
            do {
                List<JSONObject> data = new ArrayList<>(jdbcTemplate.query(sqlSource.getSql(), buildArgs(parameterMappings), convertJson));
                dataAll.addAll(data);
                size = data.size();
                if (DBType.MYSQL.toString().equals(properties.getDbType().toString())) {
                    properties.getParam().setPage(properties.getParam().getPage() + properties.getParam().getOffset());
                } else if (DBType.ORACLE.toString().equals(properties.getDbType().toString())) {
                    properties.getParam().setPage(properties.getParam().getPage() + properties.getParam().getOffset());
                    properties.getParam().setOffset(properties.getParam().getOffset() + properties.getParam().getOffset());
                }
            } while (size >0);
        } else {
            dataAll = jdbcTemplate.query(sqlSource.getSql(), buildArgs(parameterMappings), convertJson);
        }
        // 启用增量只记录最后 ID
        if (properties.getIsIncrement()) {
            // 记录增量 Id
            long maxPrimaryKey = dataAll.stream().mapToLong(item -> item.getLong(properties.getPrimaryKey()))
                    .max().orElse(properties.getParam().getIncrementId());
            properties.getParam().setIncrementId(maxPrimaryKey);
        }
        // 数据库同步配置
        return dataAll;
    }

    @Override
    void finishAfter() {
        // 持久化数据库采集配置
    }
}
