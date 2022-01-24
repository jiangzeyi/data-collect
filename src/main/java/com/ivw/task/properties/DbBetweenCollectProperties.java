package com.ivw.task.properties;

import com.ivw.task.enums.DBType;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

/**
 * @author Yi
 */
@Data
@Builder
public class DbBetweenCollectProperties extends AbstractDbCollectProperties {

    private String beginDate;

    private String endDate;

}
