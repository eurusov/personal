package util;

import dao.creator.HibernateDaoCreator;
import dao.creator.JdbcDaoCreator;
import dao.creator.UserDaoCreator;
import service.DBException;
import service.DaoType;

public class DBService {

    public static UserDaoCreator userDaoCreator;

    public static void setDaoCreatorType(DaoType daoType) throws DBException {
        if (daoType == DaoType.HIBERNATE) {
            userDaoCreator = new HibernateDaoCreator();
        } else if (daoType == DaoType.JDBC) {
            userDaoCreator = new JdbcDaoCreator();
        }
    }

//    private static SessionFactory createSessionFactory() {
//        Configuration configuration = new Configuration().configure();
//        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
//        builder.applySettings(configuration.getProperties());
//        ServiceRegistry serviceRegistry = builder.build();
//        return configuration.buildSessionFactory(serviceRegistry);
//    }


//    public static DaoContext getNewConnection() {
//        try {
//            Class.forName(StringConst.JDBC_DRIVER_NAME);
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//        Connection connection = null;
//        try {
//            connection = DriverManager.getConnection(StringConst.JDBC_URL, StringConst.USERNAME, StringConst.PASSWORD);
//            connection.setAutoCommit(false);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return new JdbcConnection(connection);
//    }

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
