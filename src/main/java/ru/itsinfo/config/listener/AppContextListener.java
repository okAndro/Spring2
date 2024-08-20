package ru.itsinfo.config.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

public class AppContextListener implements ServletContextListener {

    // Метод вызывается при запуске веб-приложения
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // Вывод сообщения в консоль о запуске приложения
        System.out.println("************** Starting up! **************");
    }

    // Метод вызывается при завершении работы веб-приложения
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Вывод сообщения в консоль о завершении работы приложения
        System.out.println("************** Shutting down! **************");
        System.out.println("Destroying Context...");
        System.out.println("Calling MySQL AbandonedConnectionCleanupThread checkedShutdown");

        // Здесь можно добавить код для завершения фоновых задач, которые могут использовать базу данных
        // Например, завершение потоков или задач, использующих соединения с базой данных
        // Также можно закрыть пулы соединений с базой данных (если они используются)

        // Дерегистрация JDBC-драйверов, зарегистрированных в текущем контексте ClassLoader:
        // Получаем текущий ClassLoader веб-приложения
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        // Проходим по всем зарегистрированным драйверам
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            // Проверяем, был ли драйвер зарегистрирован текущим ClassLoader
            if (driver.getClass().getClassLoader() == cl) {
                // Если да, дерегистрируем его
                try {
                    System.out.println("Deregistering JDBC driver " + driver);
                    DriverManager.deregisterDriver(driver);
                } catch (SQLException ex) {
                    System.out.println("Error deregistering JDBC driver " + driver);
                    ex.printStackTrace();
                }
            } else {
                // Если драйвер не был зарегистрирован текущим ClassLoader,
                // то его дерегистрация может повлиять на другие приложения, поэтому не делаем этого
                System.out.println("Not deregistering JDBC driver " + driver + " as it does not belong to this webapp's ClassLoader");
            }
        }
    }
}
