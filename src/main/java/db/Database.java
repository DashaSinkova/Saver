package db;

import model.Incident;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private Connection connection;

    public Database(Connection connection) {
        this.connection = connection;
    }

    public List<Incident> findAll() {
        String query = "select * from incidents";
        LocalDateTime date = getLastDate();
        if (!date.equals(LocalDateTime.of(1999, 01, 01, 01, 01))) {
            String dateFormat = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            query = "select * from incidents where created > '" + dateFormat + "'";
        }
        List<Incident> incidents = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    incidents.add(new Incident(
                            resultSet.getInt("id"),
                            resultSet.getString("title"),
                            resultSet.getTimestamp("created").toLocalDateTime()
                    ));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!incidents.isEmpty()) {
            date = incidents.get(incidents.size() - 1).getCreated();
            insertDate(date);
        }
        return incidents;
    }

    private void insertDate(LocalDateTime date) {
        try (PreparedStatement statement = connection.prepareStatement("insert into dates (date) values (?)")) {
            statement.setTimestamp(1, Timestamp.valueOf(date));
            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private LocalDateTime getLastDate() {
        LocalDateTime res = LocalDateTime.of(1999, 01, 01, 01, 01);
        try (PreparedStatement statement = connection.prepareStatement("select date from dates where id=(select max(id) from dates)")) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    res = resultSet.getTimestamp("date").toLocalDateTime();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }
}


