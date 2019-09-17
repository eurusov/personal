package dao;

import daoContext.DaoContext;
import model.User;
import org.hibernate.Query;
import org.hibernate.Session;
import util.DBException;

import java.util.List;

public class UserDaoHibernate implements UserDao, AutoCloseable {
    private DaoContext daoContext;

    public UserDaoHibernate(DaoContext daoContext) {
        this.daoContext = daoContext;
    }

    private Session getSession() {
        return (Session) daoContext.getContext();
    }

    @Override
    public void addUser(User user) throws DBException {
        getSession().save(user);
    }

    @Override
    public User getUser(long id) {
        return (User) getSession().get(User.class, id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> getAllUsers() {
        Query query = getSession().createQuery("FROM User");
        return (List<User>) query.list();
    }

    @Override
    public boolean updateUser(User user) throws DBException {
        getSession().save(user);
        return true;
    }

    @Override
    public boolean deleteUser(long id) throws DBException {
        User user = getUser(id);
        if (user == null) {
            return false;
        }
        getSession().delete(getUser(id));
        return true;
    }

    @Override
    public void close() {
//        getSession().getTransaction().commit();
        getSession().close();
    }
}
