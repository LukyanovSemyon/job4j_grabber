package ru.job4j.grabber;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class SqlRuParse implements Parse {
    public SqlRuParse() {
    }

    public static void main(String[] args) throws Exception {
        PsqlStore pS = new PsqlStore(getProperties());
        List<Post> list = new SqlRuParse().list("https://www.sql.ru/forum/job-offers/");
        for (Post p : list) {
            pS.save(p);
        }
        System.out.println(pS.findById("5"));
        System.out.println(pS.getAll());
        pS.close();
}

    private static Properties getProperties() {
        Properties prs = new Properties();
        try (InputStream io = SqlRuParse.class.getClassLoader()
                .getResourceAsStream("grabber.properties")) {
            prs.load(io);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prs;
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

    @Override
    public List<Post> list(String link) throws IOException {
        List<Post> list = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            Document doc = Jsoup.connect(link + i).get();
            Elements row = doc.select(".postslisttopic");
            for (Element td : row) {
                Element href = td.child(0);
                list.add(detail(href.attr("href")));
            }
        }
        return list;
    }

    @Override
    public Post detail(String link) throws IOException {
        Document doc = Jsoup.connect(link).get();
        Elements messageHeader = doc.select(".messageHeader");
        Elements msgBody = doc.select(".msgBody");
        Elements msgFooter = doc.select(".msgFooter");
        Element title = messageHeader.get(0);
        Element body = msgBody.get(1);
        String date = msgFooter.get(0).text().split("\\[")[0];
        return new Post(title.text(), body.text(), link, date(date));
    }
}
