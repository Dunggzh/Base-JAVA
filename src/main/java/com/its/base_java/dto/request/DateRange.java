package com.its.base_java.dto.request;

import lombok.Data;

import java.util.Date;

@Data
public class DateRange {
    private final Date startDate;
    private final Date endDate;

    public DateRange(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

}
