package com.azot.course.util;



import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.IOException;
import java.util.logging.*;

@WebListener
public class AppContextListener implements ServletContextListener {

    private static final Logger logger = Logger.getLogger(AppContextListener.class.getName());

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

        try {
            FileHandler fileHandler = new FileHandler("C:\\Users\\АзаматLOX\\spring_course_mvc/studentSearch.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            fileHandler.setLevel(Level.ALL);
            logger.addHandler(fileHandler);
            logger.setLevel(Level.ALL);
            logger.info("Приложение studentSearch запущено.");
            fileHandler.flush();


        } catch (IOException e) {
            logger.severe("Ошибка инициализации логгера: " + e.getMessage());

        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        logger.info("Приложение studentSearch завершено.");

        for (Handler handler : logger.getHandlers()) {
            handler.close();
        }
    }
}
