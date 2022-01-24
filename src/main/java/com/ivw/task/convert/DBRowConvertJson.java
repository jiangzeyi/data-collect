package com.ivw.task.convert;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;


/**
 * @author Yi
 * 用于转换查询的数据
 */
public class DBRowConvertJson implements RowMapper<JSONObject> {
    private final Logger logger = LoggerFactory.getLogger(DBRowConvertJson.class);
    @Override
    public JSONObject mapRow(ResultSet rs, int rowNum) throws SQLException {
        JSONObject json = new JSONObject();
        logger.info("rs：{}",rs);
        logger.info("rownum：{}", rowNum);
        ResultSetMetaData metaData = rs.getMetaData();
        for (int i = 1; i < metaData.getColumnCount(); i++) {
            json.put(metaData.getColumnName(i),rs.getObject(i));
        }
        return json;
    }
}
