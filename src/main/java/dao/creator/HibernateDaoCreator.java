package dao.creator;

import dao.UserDaoHibernate;
import dao.UserDao;
import dao.context.DaoContext;
import dao.context.HibernateSession;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import service.DBException;
import util.DBService;

public class HibernateDaoCreator implements UserDaoCreator<Session> {

    private static SessionFactory sessionFactory;

    public HibernateDaoCreator() throws DBException {
        if (sessionFactory == null) {
            sessionFactory = DBService.createSessionFactory();
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

}
