package com.bw.reference.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

public class DateTimeUtil {

    /**
     * Converts the LocalDate to a java.util.Date, considering the Local Time
     *
     * @param localDate
     * @param localTime e.g LocalTime.MAX
     * @return
     */
    public static Date toDate(LocalDate localDate, LocalTime localTime) {
        LocalDateTime localDateTime = localDate.atTime(localTime);
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}
