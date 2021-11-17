package ru.job4j.grabber.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import static java.util.Map.entry;

public class SqlRuDateTimeParser implements DateTimeParser {

    private static final Map<String, String> MONTHS = Map.ofEntries(
            entry("янв", "01"), entry("фев", "02"), entry("мар", "03"),
            entry("апр", "04"), entry("май", "05"), entry("июн", "06"),
            entry("июл", "07"), entry("авг", "08"), entry("сен", "09"),
            entry("окт", "10"), entry("ноя", "11"), entry("дек", "12"));

    @Override
    public LocalDateTime parse(String parse) {
        LocalDateTime result = null;
        DateTimeFormatter dateFormat;
        if (parse.contains("сегодня") || parse.contains("вчера")) {
            int hour = Integer.parseInt(parse.split(" ")[1].split(":")[0]);
            int minute = Integer.parseInt(parse.split(" ")[1].split(":")[1]);
            if (parse.contains("сегодня")) {
                result = LocalDateTime.now().withHour(hour).withMinute(minute);
            } else if (parse.contains("вчера")) {
                result = LocalDateTime.now().minusDays(1).withHour(hour).withMinute(minute);
            }
        } else {
            String month = parse.split(" ")[1];
            String data = parse.replace(month, MONTHS.get(month));
            dateFormat = DateTimeFormatter.ofPattern("d MM yy, HH:mm");
            if (data.split(" ")[0].length() < 2) {
                data = data.substring(0, 14);
            }
            result = LocalDateTime.parse(data, dateFormat);
        }
        return result;
    }
}