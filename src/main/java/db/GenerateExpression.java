package db;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GenerateExpression {
    public static void main(String[] args) {
        LocalDateTime date = LocalDateTime.parse("2021-09-23T00:31:13");
        //LocalDateTime date = LocalDateTime.now();
        for (int i = 0; i < 10; i++) {
            System.out.println("insert into incidents(title, created) values ('WAN Балтика', '" + date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "');");
            date = date.plusDays(1);
        }
    }
}
