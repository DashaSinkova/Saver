package quartz;

import db.Database;
import io.Writer;
import model.Incident;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import java.sql.*;

import java.util.List;

public class Saver {

    private static Connection initConnection() throws Exception {
        Class.forName("org.postgresql.Driver");
        String url = "jdbc:postgresql://localhost:5432/complete";
        String login = "postgres";
        String password = "password";
        return DriverManager.getConnection(url, login, password);
    }

    public static void main(String[] args) throws Exception {
            JobDataMap data = new JobDataMap();
            Connection connection = initConnection();
            data.put("Database", new Database(connection));
            JobDetail job = JobBuilder.newJob(LoadIncidents.class).usingJobData(data).build();
            Trigger trigger = TriggerBuilder.newTrigger().startNow().withSchedule(CronScheduleBuilder.cronSchedule("0 0/1 * 1/1 * ? *")).build();
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();
            scheduler.scheduleJob(job, trigger);
    }

    public static class LoadIncidents implements Job {
        private Database database;

        @Override
        public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
            database = (Database) jobExecutionContext.getJobDetail().getJobDataMap().get("Database");
            List<Incident> incidents = database.findAll();
            if (!incidents.isEmpty()) {
                Writer writer = new Writer(incidents);
                writer.writeToFile();
                System.out.println("Данные записаны");
            } else {
                System.out.println("Нет новых данных");
            }
        }
    }
}
