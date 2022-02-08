package com.ivw.task.param;

import lombok.Data;

@Data
public class DbDateBetweenCollectParam extends DefaultDbCollectParam {

    private String beginDate;

    private String endDate;
}
