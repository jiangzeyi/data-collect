package com.ivw.task.collect;

import com.ivw.task.convert.DBRowConvertJson;
import com.ivw.task.properties.AbstractCollectProperties;
import com.ivw.task.sql.SqlSource;
import lombok.Data;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

/**
 * @author Yi
 */
@Data
public abstract class DbAbstractCollect<T extends AbstractCollectProperties> extends AbstractCollect<T>{

    /**
     * JdbcTemplate
     */
    final JdbcTemplate jdbcTemplate;

    /**
     * 用于进行数据转换
     */
    final DBRowConvertJson convertJson = new DBRowConvertJson();

    /**
     * SQL 解析器
     */
    SqlSource sqlSource;

    static final String UPDATE_COLLECT_PARAM_SQL = "update DP_SYS_TASK set COLLECT_PARAM = to_char(#{param}) where id = #{taskId}";

    public DbAbstractCollect(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    abstract Object[] buildArgs(List<String> parameterMappings);
}
