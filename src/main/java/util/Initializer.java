package util;

import daoContext.producer.SessionProducer;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Initializer implements ServletContextListener {

    public void contextInitialized(ServletContextEvent event) {
        ServletContext servletContext = event.getServletContext();
        configDaoType(servletContext);

//        SessionProducer.getInstance();
    }

    public void contextDestroyed(ServletContextEvent event) {
//        try {
//            SessionProducer.getInstance().getDaoContext().close(); // Free all resources
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    private void configDaoType(ServletContext servletContext) {
        InputStream config = servletContext.getResourceAsStream(StringConst.CONFIG);
        Properties properties = new Properties();
        try {
            properties.load(config);
        } catch (IOException e) {
            e.printStackTrace();
        }
        DBService.daoType = DaoType.valueOf(properties.getProperty("dao.type").trim().toUpperCase());
    }
}
