package ru.job4j.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

import static org.quartz.JobBuilder.*;
import static org.quartz.TriggerBuilder.*;
import static org.quartz.SimpleScheduleBuilder.*;

public class AlertRabbit {
    private Connection connection;
    private final Properties properties;

    public AlertRabbit(Properties properties) throws SQLException {
        this.properties = properties;
        initConnection();
    }

    private static Properties getProperties() {
        Properties prs = new Properties();
        try (InputStream io = AlertRabbit.class.getClassLoader()
                .getResourceAsStream("rabbit.properties")) {
            prs.load(io);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prs;
    }

    private void initConnection() throws SQLException {
        String url = properties.getProperty("postgresql.url");
        String login = properties.getProperty("postgresql.login");
        String password = properties.getProperty("postgresql.password");
        connection = DriverManager.getConnection(url, login, password);
    }

    public void close() throws Exception {
        if (connection != null) {
            connection.close();
        }
    }

    public void job() {
        try {
            createTable();
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();
            JobDataMap data = new JobDataMap();
            data.put("connection", connection);
            JobDetail job = newJob(Rabbit.class)
                    .usingJobData(data)
                    .build();
            int rabbitInterval = Integer.parseInt(getProperties().getProperty("rabbit.interval"));
            SimpleScheduleBuilder times = simpleSchedule()
                    .withIntervalInSeconds(rabbitInterval)
                    .repeatForever();
            Trigger trigger = newTrigger()
                    .startNow()
                    .withSchedule(times)
                    .build();
            scheduler.scheduleJob(job, trigger);
            Thread.sleep(10000);
            scheduler.shutdown();
            System.out.println(connection);
            close();
        } catch (Exception se) {
            se.printStackTrace();
        }
    }

    public void createTable() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            String sql;
            sql = "create table if not exists rabbit(id serial primary key, created_date long);";
            statement.execute(sql);
        }
    }

    public static void main(String[] args) throws SQLException {
        AlertRabbit rabbit = new AlertRabbit(getProperties());
        rabbit.job();
    }

    public static class Rabbit implements Job {

        public Rabbit() {
            System.out.println(hashCode());
        }

        @Override
        public void execute(JobExecutionContext context) {
            System.out.println("Rabbit runs here ...");
            Connection s = (Connection) context.getJobDetail().getJobDataMap().get("connection");
            try (PreparedStatement statement = s
                    .prepareStatement("insert into rabbit(created_date) values(?)")) {
                statement.setLong(1, System.currentTimeMillis());
                statement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}