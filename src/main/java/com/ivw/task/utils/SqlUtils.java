package com.ivw.task.utils;

import cn.hutool.core.collection.ArrayIter;

public class SqlUtils {
    public static String fillParams(String sql, Object... params) {
        return fillParams(sql, new ArrayIter<>(params));
    }
}
