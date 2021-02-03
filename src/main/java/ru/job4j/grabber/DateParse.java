package ru.job4j.grabber;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DateParse {
    public static Date date(String date) {
        String num = date.trim().split(",")[0];
        String time = date.split(",")[1];
        int hour = Integer.parseInt(time.trim().split(":")[0]);
        int minute = Integer.parseInt(time.trim().split(":")[1]);
        Calendar calendar = Calendar.getInstance();
        int day;
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        Map<String, Integer> mon = new HashMap();
        mon.put("янв", Calendar.JANUARY);
        mon.put("фев", Calendar.FEBRUARY);
        mon.put("мар", Calendar.MARCH);
        mon.put("апр", Calendar.APRIL);
        mon.put("май", Calendar.MAY);
        mon.put("июн", Calendar.JUNE);
        mon.put("июл", Calendar.JULY);
        mon.put("авг", Calendar.AUGUST);
        mon.put("сен", Calendar.SEPTEMBER);
        mon.put("окт", Calendar.OCTOBER);
        mon.put("ноя", Calendar.NOVEMBER);
        mon.put("дек", Calendar.DECEMBER);
        if (num.equals("сегодня")) {
            day = calendar.get(Calendar.DATE);
        } else if (num.equals("вчера")) {
            day = calendar.get(Calendar.DATE) - 1;
        } else {
            day = Integer.parseInt(num.split(" ")[0]);
            String mm = num.trim().split(" ")[1];
            month = mon.get(mm);
            year = Integer.parseInt(num.split(" ")[2]) + 2000;
        }
        calendar.set(year, month, day, hour, minute);
        return calendar.getTime();
    }
}
