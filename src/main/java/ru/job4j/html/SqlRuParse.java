package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import ru.job4j.grabber.utils.SqlRuDateTimeParser;

import java.io.IOException;
import java.time.LocalDateTime;

public class SqlRuParse {
    public static void main(String[] args) throws Exception {
        String link = "https://www.sql.ru/forum/1325330/lidy-be-fe-senior-cistemnye-analitiki-qa-i-devops-moskva-do-200t";
        SqlRuParse sqlRuParse = new SqlRuParse();
        Post post = sqlRuParse.getPost(link);
        System.out.println(post);
    }

    public Post getPost(String link) throws IOException {
        Post result;
        Document doc = Jsoup.connect(link).get();
        Element row = doc.select(".msgTable").get(0);
        String title = row.child(0).child(0).child(0).text();
        String description = row.child(0).child(1).child(1).text();
        String date = row.child(0).child(2).child(0).text().substring(0, 16);
        SqlRuDateTimeParser dateTimeParser = new SqlRuDateTimeParser();
        LocalDateTime created = dateTimeParser.parse(date);
        result = new Post(0, title, link, description, created);
        return result;
    }
}