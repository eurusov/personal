package util;

import dao.creator.HibernateDaoCreator;
import dao.creator.JdbcDaoCreator;
import dao.creator.UserDaoCreator;
import model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import service.DBException;
import service.DaoType;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBService {

    private static UserDaoCreator userDaoCreator;

    public static UserDaoCreator getUserDaoCreator() {
        return userDaoCreator;
    }

    public static void setDaoCreatorType(DaoType daoType) throws DBException {
        if (daoType == DaoType.HIBERNATE) {
            userDaoCreator = new HibernateDaoCreator();
        } else if (daoType == DaoType.JDBC) {
            userDaoCreator = new JdbcDaoCreator();
        }
    }

    private static Configuration getHibernateConfiguration() throws IOException {
        Configuration configuration = new Configuration();
        Properties properties = new Properties();
        properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("hibernate.properties"));
        configuration.setProperties(properties);

        configuration.addAnnotatedClass(User.class);
        return configuration;
    }

    public static SessionFactory createSessionFactory() throws DBException {
        Configuration configuration;
        try {
            configuration = getHibernateConfiguration();
        } catch (IOException e) {
            e.printStackTrace();
            throw new DBException(e);
        }
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    public static Connection getMysqlConnection() throws DBException {
        try {
            DriverManager.registerDriver((Driver) Class.forName(StringConst.JDBC_DRIVER_NAME).newInstance());
            return DriverManager.getConnection(StringConst.JDBC_URL, StringConst.USERNAME, StringConst.PASSWORD);
        } catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new DBException(e);
        }
    }


//    public static void deleteAll() {
//        Session sess = sessionFactory.openSession();
//        Transaction tx = sess.beginTransaction();
//        sess.createQuery("delete from User").executeUpdate();
//        tx.commit();
//        sess.close();
//    }

//    public static void printConnectInfo() {
//        try {
//            SessionFactoryImpl sessionFactoryImpl = (SessionFactoryImpl) sessionFactory;
//            Connection connection = sessionFactoryImpl.getConnectionProvider().getConnection();
////            Connection connection = sessionFactory.getSessionFactoryOptions().getServiceRegistry()
////                    .getService(ConnectionProvider.class).getConnection();
//            System.out.println("DB name: " + connection.getMetaData().getDatabaseProductName());
//            System.out.println("DB version: " + connection.getMetaData().getDatabaseProductVersion());
//            System.out.println("Driver: " + connection.getMetaData().getDriverName());
//            System.out.println("Autocommit: " + connection.getAutoCommit());
//            System.out.println();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

}
