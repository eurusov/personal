package dao;

import model.User;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import util.DBException;
import util.DBService;

import java.util.List;

public class UserDaoHibernate implements UserDao, AutoCloseable {
    private Session session;
    private Transaction transaction;

    public UserDaoHibernate() {
//        new DBService();
        this.session = DBService.getSessionFactory().openSession();
        transaction = this.session.beginTransaction();
    }

    @Override
    public void addUser(User user) throws DBException {
        session.save(user);
    }

    @Override
    public User getUser(long id) {
        return (User) session.get(User.class, id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> getAllUsers() {
        Query query = session.createQuery("FROM User");
        List<User> users = query.list();
        return users;
    }

    @Override
    public boolean updateUser(User user) throws DBException {
        session.save(user);
        return true;
    }

    @Override
    public boolean deleteUser(long id) throws DBException {
        User user = getUser(id);
        if (user == null) {
            return false;
        }
        session.delete(getUser(id));
        return true;
    }

    @Override
    public void close() throws Exception {
        transaction.commit();
        session.close();
    }
}
