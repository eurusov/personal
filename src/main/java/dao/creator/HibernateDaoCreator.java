package dao.creator;

import dao.UserDaoHibernate;
import dao.UserDao;
import dao.context.DaoContext;
import dao.context.HibernateSession;
import model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import service.DBException;

import java.io.IOException;
import java.util.Properties;

public class HibernateDaoCreator implements UserDaoCreator<Session> {

    private static SessionFactory sessionFactory;

    public HibernateDaoCreator() throws DBException {
        if (sessionFactory == null) {
            sessionFactory = createSessionFactory();
        }
    }

    @Override
    public UserDao createDao(DaoContext<Session> daoContext) {
        return new UserDaoHibernate(daoContext.getContext());
    }

    @Override
    public DaoContext<Session> createDaoContext() {
        return new HibernateSession(sessionFactory.openSession());
    }

    @Override
    public void close() {
//        sessionFactory.getCurrentSession().close();
        sessionFactory.close();
    }

    private static Configuration getHibernateConfiguration() throws IOException {
        Configuration configuration = new Configuration();
        Properties properties = new Properties();
        properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("hibernate.properties"));
        configuration.setProperties(properties);

        configuration.addAnnotatedClass(User.class);
        return configuration;
    }

    private static SessionFactory createSessionFactory() throws DBException {
        Configuration configuration = null;
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

//        Configuration configuration = new Configuration().configure();
//        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
//        builder.applySettings(configuration.getProperties());
//        ServiceRegistry serviceRegistry = builder.build();
//        return configuration.buildSessionFactory(serviceRegistry);
    }
}
