package io;

import model.Incident;

import java.io.BufferedWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Writer {
    private List<Incident> incidents;
    public Writer(List<Incident> incidents) {
        this.incidents = incidents;
    }

    public String writeToFile() {
        String date = incidents.get(0).getCreated().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        String path = "C:\\Projects\\Complete\\files\\report_" + date + ".txt";
        try (BufferedWriter writer = new BufferedWriter(new java.io.FileWriter(path))) {
            writer.write("Отчет за " + date + System.lineSeparator());
            int count = 1;
            for (int i = 0; i < incidents.size(); i++) {
                writer.write(count + ". " + incidents.get(i).toString());
                count++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path;
    }
}
