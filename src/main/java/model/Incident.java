package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Incident {
    private int id;
    private String title;
    private LocalDateTime created;

    public Incident(int id, String title, LocalDateTime created) {
        this.id = id;
        this.title = title;
        this.created = created;
    }

    @Override
    public String toString() {
        return "Incident{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", created=" + created.format(DateTimeFormatter.ofPattern(("dd-MM-yyyy HH:mm:ss"))) +
                '}' + System.lineSeparator();
    }


    public LocalDateTime getCreated() {
        return created;
    }

}
