package com.ivw.task.sql;

import java.util.List;

/**
 * @author Yi
 */
public class SqlSource {
    private final String openToken;
    private final String closeToken;
    private final List<String> parameterMappings;
    private String sql;

    public SqlSource(String openToken, String closeToken, List<String> parameterMappings) {
        this.openToken = openToken;
        this.closeToken = closeToken;
        this.parameterMappings = parameterMappings;
    }

    public void parse(String text) {
        if (sql != null && !sql.isEmpty()) {
            return;
        }
        if (text == null || text.isEmpty()) {
            return;
        }
        int start = text.indexOf(openToken);
        if (start == -1) {
            return;
        }
        char[] src = text.toCharArray();
        int offset = 0;
        final StringBuilder builder = new StringBuilder();
        StringBuilder expression = null;
        do {
            if (start > 0 && src[start - 1] == '\\') {
                builder.append(src,offset,start - offset - 1).append(openToken);
            } else {
                if (expression == null) {
                    expression = new StringBuilder();
                } else {
                    expression.setLength(0);
                }
                builder.append(src, offset, start - offset);
                // 跳过 openToken
                offset = start + openToken.length();
                int end = text.indexOf(closeToken, offset);
                while (end > -1) {
                    if (end > offset && src[end - 1] == '\\') {
                        expression.append(src, offset,end - offset -1).append(closeToken);
                        offset = end + closeToken.length();
                        end = text.indexOf(closeToken,offset);
                    } else {
                        expression.append(src,offset, end - offset);
                        break;
                    }
                }
                if (end == -1) {
                    builder.append(src, start, src.length - start);
                    offset = src.length;
                } else {
                    builder.append(handleToken(expression.toString()));
                    offset = end + closeToken.length();
                }
            }
            start = text.indexOf(openToken, offset);
        } while (start > -1);
        if (offset < src.length) {
            builder.append(src, offset, src.length - offset);
        }
        sql =  builder.toString();
    }

    private String handleToken(String content) {
        parameterMappings.add(content);
        return "?";
    }

    public String getOpenToken() {
        return openToken;
    }

    public String getCloseToken() {
        return closeToken;
    }

    public List<String> getParameterMappings() {
        return parameterMappings;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }
}
