package com.ivw.task.properties;

import com.ivw.task.enums.DBType;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * @author Yi
 */
@Data
@NoArgsConstructor
public class DbBetweenCollectProperties extends AbstractDbCollectProperties {

    private String beginDate;

    private String endDate;

}
