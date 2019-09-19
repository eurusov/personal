package dao.creator;

import dao.UserDaoHibernate;
import dao.UserDao;
import dao.context.DaoContext;
import dao.context.HibernateSession;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateDaoCreator implements UserDaoCreator<Session> {

    private static SessionFactory sessionFactory;

    public HibernateDaoCreator() {
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

    private static SessionFactory createSessionFactory() {
        Configuration configuration = new Configuration().configure();
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }
}
