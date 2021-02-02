package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Calendar;
import java.util.Date;

public class SqlRuParse {
    public static void main(String[] args) throws Exception {
        for (int i = 1; i <= 5; i++) {
            Document doc = Jsoup.connect("https://www.sql.ru/forum/job-offers/" + i).get();
            Elements row = doc.select("tr");
            for (Element td : row) {
                Elements postslisttopic = td.select(".postslisttopic");
                for (Element a : postslisttopic) {
                    Element href = a.child(0);
                    String date = td.child(5).text();
                    System.out.println(href.attr("href"));
                    System.out.println(href.text());
                    System.out.println(date(date));
                }
            }
        }
    }

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
            switch (mm) {
                case ("янв"):
                    month = Calendar.JANUARY;
                    break;
                case ("фев"):
                    month = Calendar.FEBRUARY;
                    break;
                case ("мар"):
                    month = Calendar.MARCH;
                    break;
                case ("апр"):
                    month = Calendar.APRIL;
                    break;
                case ("май"):
                    month = Calendar.MAY;
                    break;
                case ("июн"):
                    month = Calendar.JUNE;
                    break;
                case ("июл"):
                    month = Calendar.JULY;
                    break;
                case ("авг"):
                    month = Calendar.AUGUST;
                    break;
                case ("сен"):
                    month = Calendar.SEPTEMBER;
                    break;
                case ("окт"):
                    month = Calendar.OCTOBER;
                    break;
                case ("ноя"):
                    month = Calendar.NOVEMBER;
                    break;
                case ("дек"):
                    month = Calendar.DECEMBER;
                    break;
                default:
            }
            year = Integer.parseInt(num.split(" ")[2]) + 2000;
        }
        calendar.set(year, month, day, hour, minute);
        return calendar.getTime();
    }
}
