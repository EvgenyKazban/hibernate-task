package jm.task.core.jdbc.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static SessionFactory sessionFactory;

    private static final String URL = "jdbc:mysql://localhost/task";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    private Util() {

    }

    private static SessionFactory buildSessionFactory() {
        try {
            Configuration cfg = new Configuration()
                    .addAnnotatedClass(jm.task.core.jdbc.model.User.class)
                    .setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLInnoDBDialect")
                    .setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver")
                    .setProperty("hibernate.connection.url", URL)
                    .setProperty("hibernate.connection.username", USER)
                    .setProperty("hibernate.connection.password", PASSWORD);
            return cfg.buildSessionFactory();

        }
        catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }


    //Hibernate session factory
    public static SessionFactory getSessionFactory() {
        SessionFactory localSessionFactory = sessionFactory;
        if (localSessionFactory == null) {
            synchronized (Util.class) {
                localSessionFactory = sessionFactory;
                if (sessionFactory == null) {
                    sessionFactory = localSessionFactory = buildSessionFactory();
                }
            }
        }
        return localSessionFactory;
    }

}