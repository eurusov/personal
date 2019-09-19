package dao.context.transaction;

import service.DBException;
import org.hibernate.HibernateException;
import org.hibernate.Transaction;

public class HibernateTransaction implements DaoTransaction {

    private Transaction transaction;

    public HibernateTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    @Override
    public void commit() throws DBException {
        try {
            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            throw new DBException(e);
        }
    }
}
