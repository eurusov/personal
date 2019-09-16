package service;

import org.hibernate.Session;
import org.hibernate.Transaction;
import util.DBService;

public class UserHibernateSessionHandler implements AutoCloseable {
    private Session session;
    private Transaction transaction;

    public UserHibernateSessionHandler() {
        this.session = DBService.getSessionFactory().openSession();
        transaction = this.session.beginTransaction();
    }

    @Override
    public void close() throws Exception {
        transaction.commit();
        session.close();
    }
}
