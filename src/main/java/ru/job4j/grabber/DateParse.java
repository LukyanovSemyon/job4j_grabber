package ru.job4j.grabber;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DateParse {
    private static final Map<String, Integer> MONTHS = new HashMap<>() {{
        put("янв", Calendar.JANUARY);
        put("фев", Calendar.FEBRUARY);
        put("мар", Calendar.MARCH);
        put("апр", Calendar.APRIL);
        put("май", Calendar.MAY);
        put("июн", Calendar.JUNE);
        put("июл", Calendar.JULY);
        put("авг", Calendar.AUGUST);
        put("сен", Calendar.SEPTEMBER);
        put("окт", Calendar.OCTOBER);
        put("ноя", Calendar.NOVEMBER);
        put("дек", Calendar.DECEMBER);
    }};

    public static Date date(String date) {
        String num = date.trim().split(",")[0];
        String time = date.split(",")[1];
        int hour = Integer.parseInt(time.trim().split(":")[0]);
        int minute = Integer.parseInt(time.trim().split(":")[1]);
        Calendar calendar = Calendar.getInstance();
        int day;
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        if (num.equals("сегодня")) {
            day = calendar.get(Calendar.DATE);
        } else if (num.equals("вчера")) {
            day = calendar.get(Calendar.DATE) - 1;
        } else {
            day = Integer.parseInt(num.split(" ")[0]);
            String mm = num.trim().split(" ")[1];
            month = MONTHS.get(mm);
            year = Integer.parseInt(num.split(" ")[2]) + 2000;
        }
        calendar.set(year, month, day, hour, minute);
        return calendar.getTime();
    }
}
