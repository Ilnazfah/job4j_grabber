package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.job4j.grabber.Parse;
import ru.job4j.grabber.utils.DateTimeParser;
import ru.job4j.grabber.utils.SqlRuDateTimeParser;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SqlRuParse implements Parse {
    private final DateTimeParser dateTimeParser;

    public SqlRuParse(DateTimeParser dateTimeParser) {
        this.dateTimeParser = dateTimeParser;
    }

    @Override
    public List<Post> list(String link) {
        List<Post> posts = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            try {
                Document doc = Jsoup.connect(String.format("%s/%s", link, i)).get();
                Elements row = doc.select(".postslisttopic");
                for (Element td : row) {
                    Element href = td.child(0);
                    posts.add(detail(href.attr("href")));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return posts;
    }

    public Post detail(String link) {
        Post post = null;
        try {
            Document doc = Jsoup.connect(link).get();
            Element row = doc.select(".msgTable").get(0);
            String title = row.child(0).child(0).child(0).text();
            String description = row.child(0).child(1).child(1).text();
            String date = row.child(0).child(2).child(0).text().substring(0, 16);
            LocalDateTime created;
            created = dateTimeParser.parse(date);
            post = new Post(title, link, description, created);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return post;
    }

    public static void main(String[] args) {
        DateTimeParser dateTimeParser = new SqlRuDateTimeParser();
        SqlRuParse sqlRuParse = new SqlRuParse(dateTimeParser);
        List<Post> posts = sqlRuParse.list("https://www.sql.ru/forum/job-offers");
        for (Post p : posts) {
            System.out.println(p.toString());
        }
    }
}