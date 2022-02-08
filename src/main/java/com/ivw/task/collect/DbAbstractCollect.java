package com.ivw.task.collect;

import com.ivw.task.convert.DBRowConvertJson;
import com.ivw.task.properties.AbstractCollectProperties;
import com.ivw.task.sql.SqlSource;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

/**
 * @author Yi
 */
public abstract class DbAbstractCollect<T extends AbstractCollectProperties> extends AbstractCollect<T>{

    /**
     * JdbcTemplate
     */
    final JdbcTemplate jdbcTemplate;

    /**
     * 用于进行数据转换
     */
    final DBRowConvertJson convertJson = new DBRowConvertJson();

    public DbAbstractCollect(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    abstract Object[] buildArgs(List<String> parameterMappings);
}
