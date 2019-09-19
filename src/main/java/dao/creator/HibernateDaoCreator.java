package dao.creator;

import dao.context.DaoContext;
import dao.context.HibernateSession;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateDaoCreator implements UserDaoCreator<Session> {
    private static HibernateDaoCreator hibernateDaoCreator;
    private static SessionFactory sessionFactory;

    private HibernateDaoCreator() {
        if (sessionFactory == null) {
            sessionFactory = createSessionFactory();
        }
    }

    public static HibernateDaoCreator getInstance() {
        if (hibernateDaoCreator == null) {
            hibernateDaoCreator = new HibernateDaoCreator();
        }
        return hibernateDaoCreator;
    }

    @Override
    public DaoContext<Session> getDaoContext() {
        Session sess = sessionFactory.openSession();
        return new HibernateSession(sess);
    }

    private static SessionFactory createSessionFactory() {
        Configuration configuration = new Configuration().configure();
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    @Override
    public DaoContext<Session> get() {
        Session sess = sessionFactory.openSession();
        return new HibernateSession(sess);
    }
}
