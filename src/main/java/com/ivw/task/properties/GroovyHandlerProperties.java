package com.ivw.task.properties;

import com.ivw.task.script.IScript;
import lombok.Builder;
import lombok.Data;

import java.io.File;

/**
 * @author Yi
 */
@Data
@Builder
public  class GroovyHandlerProperties {

    private File scriptFile;

    private String scriptName;

    private Class<? extends IScript> clazz;
}
