package com.ivw.task.handler;

import cn.hutool.core.util.ReflectUtil;
import com.ivw.task.properties.GroovyHandlerProperties;
import com.ivw.task.script.IScript;
import groovy.lang.GroovyClassLoader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ClassUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

/**
 * @author Yi
 */
@Slf4j
public class GroovyDataHandler extends AbstractHandler<GroovyHandlerProperties> {


    private final GroovyClassLoader classLoader = new GroovyClassLoader(ClassUtils.getDefaultClassLoader());

    @Override
    void startBefore(){
        if (Objects.isNull(properties.getScriptName()) &&
                Objects.isNull(properties.getScriptFile())) {
            throw new RuntimeException("脚本相关参数不能为空");
        }
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(properties.getScriptFile());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 编译脚本
        properties.setClazz(classLoader.parseClass(fileReader,properties.getScriptName()));


    }

    @Override
    Object execute(Object data) {
        // 数据处理
        IScript iScript = ReflectUtil.newInstance(properties.getClazz());
        return iScript.execute(data);
    }

    @Override
    void finishAfter() {

    }

}
