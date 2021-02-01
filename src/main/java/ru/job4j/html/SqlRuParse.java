package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SqlRuParse {
    public static void main(String[] args) throws Exception {
        Document doc = Jsoup.connect("https://www.sql.ru/forum/job-offers").get();
        Elements row = doc.select("tr");
        for (Element td : row) {
            Elements postslisttopic = td.select(".postslisttopic");
            for (Element a : postslisttopic) {
                Element href = a.child(0);
                System.out.println(href.attr("href"));
                System.out.println(href.text());
                System.out.println(td.child(5).text());
            }
        }
    }
}
