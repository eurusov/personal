package daoContext.producer;

import daoContext.DaoContext;
import daoContext.HibernateSession;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class SessionProducer implements DaoContextProducer {
    private static SessionProducer sessionProducer;
    private static SessionFactory sessionFactory;

    private SessionProducer() {
        if (sessionFactory == null) {
            sessionFactory = createSessionFactory();
        }
    }

    public static SessionProducer getInstance() {
        if (sessionProducer == null) {
            sessionProducer = new SessionProducer();
        }
        return sessionProducer;
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
}
