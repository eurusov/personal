package util;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class HibernateListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent event) {
        DBService.getSessionFactory();
    }

    public void contextDestroyed(ServletContextEvent event) {
        DBService.getSessionFactory().close(); // Free all resources
    }
}
