package jm.task.core.jdbc.util;

import java.util.Properties;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;
import jm.task.core.jdbc.model.User;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Util {
    private static final String URL = "jdbc:mysql://localhost:3306/mydbtest";
    private static final String LOG = "root";
    private static final String PASS = "root";

    private static SessionFactory sessionFactory;


    public static Connection getConnection() {
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(URL, LOG, PASS);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static SessionFactory getSessionFactory() {

        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();

                Properties properties = new Properties();
                properties.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
                properties.put(Environment.URL, URL);
                properties.put(Environment.USER, LOG);
                properties.put(Environment.PASS, PASS);
                properties.put(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");


                configuration.setProperties(properties);

                configuration.addAnnotatedClass(User.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }

}
