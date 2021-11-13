package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.job4j.grabber.utils.SqlRuDateTimeParser;

public class SqlRuParse {
    public static void main(String[] args) throws Exception {
            Document doc = Jsoup.connect("https://www.sql.ru/forum/1325330/lidy-be-fe-senior-cistemnye-analitiki-qa-i-devops-moskva-do-200t").get();
            Element row = doc.select(".msgTable").get(0);
            String description = row.child(0).child(1).child(1).text();
            String date = row.child(0).child(2).child(0).text().substring(0, 16);
            System.out.println(description);
            SqlRuDateTimeParser dateTimeParser = new SqlRuDateTimeParser();
            System.out.println(dateTimeParser.parse(date));

//        for (int i = 1; i <= 5; i++) {
//            Document doc = Jsoup.connect(String.format("https://www.sql.ru/forum/job-offers/%s", i)).get();
//            Elements row = doc.select(".postslisttopic");
//            SqlRuDateTimeParser dateTimeParser = new SqlRuDateTimeParser();
//            for (Element td : row) {
//                Element href = td.child(0);
//                System.out.println(href.attr("href"));
//                System.out.println(href.text());
//                Element parent = td.parent();
//                String date = parent.child(5).text();
//                System.out.println(dateTimeParser.parse(date));
//            }
//        }
    }
}