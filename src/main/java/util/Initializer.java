package util;

import service.DBException;
import service.DaoType;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@WebListener
public class Initializer implements ServletContextListener {

    public void contextInitialized(ServletContextEvent event) {
        ServletContext servletContext = event.getServletContext();
        try {
            configDaoType(servletContext);
        } catch (IOException | DBException e) {
            e.printStackTrace();
        }
    }

    public void contextDestroyed(ServletContextEvent event) {
        try {
            DBService.getUserDaoCreator().close(); // Free all resources
        } catch (Exception e) {
            System.out.println("Exception while closing UserDaoCreator at contextDestroyed(...)");
            e.printStackTrace();
        }
    }

    private static void configDaoType(ServletContext servletContext) throws IOException, DBException {
        InputStream config = servletContext.getResourceAsStream(StringConst.CONFIG_PATH);
        Properties properties = new Properties();
        properties.load(config);
        DBService.setDaoCreatorType(
                DaoType.valueOf(
                        properties
                                .getProperty("dao.type")
                                .trim()
                                .toUpperCase()
                )
        );
    }
}
