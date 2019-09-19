package dao.context;

import service.DBException;
import dao.context.transaction.DaoTransaction;
import dao.context.transaction.HibernateTransaction;
import org.hibernate.HibernateException;
import org.hibernate.Session;

public class HibernateSession extends AbstractDaoContext<Session> {

    public HibernateSession(Session session) {
        super(session);
    }

    @Override
    public DaoTransaction beginTransaction() {
        return new HibernateTransaction(super.getContext().beginTransaction());
    }

    @Override
    public void close() throws DBException {
        try {
            Session session = super.getContext();
            if (session.isOpen()) {
                session.close();
            }
        } catch (HibernateException e) {
            e.printStackTrace();
            throw new DBException(e);
        }
    }
}
