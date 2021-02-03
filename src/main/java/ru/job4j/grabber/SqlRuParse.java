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
        return new Post(title.text(), body.text(), link, DateParse.date(date));
    }
}
