package com.springtraining.furnitureshop.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Locale;

public class DateUtil {
    private static final String FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static String dateToString(Calendar calendar, Locale locale) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FORMAT).withLocale(locale);
        return formatter.format(LocalDateTime.ofInstant(
                calendar.toInstant(),
                calendar.getTimeZone().toZoneId()));
    }

    public static String timestampToString(Timestamp timestamp) {
        return new SimpleDateFormat(FORMAT).format(timestamp);
    }
}
