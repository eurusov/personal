package dao;

import model.User;
import org.hibernate.Query;
import org.hibernate.Session;
import service.DBException;

import java.util.List;

public class UserDaoHibernate implements UserDao {

    private Session session;

    public UserDaoHibernate(Session session) {
        this.session = session;
    }

    @Override
    public Long addUser(User user) {
        return (Long) session.save(user);
    }

    @Override
    public User getUser(Long id) {
        return (User) session.get(User.class, id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> getAllUser() {
        Query query = session.createQuery("FROM User");
        return query.list();
    }

    /* Updates only fields that are not null in updatedUser */
    @Override
    public boolean updateUser(User updatedUser) {
        User oldUser = (User) session.get(User.class, updatedUser.getId());
        if (oldUser == null) {
            return false;
        }
        String param;
        if ((param = updatedUser.getEmail()) != null) {
            oldUser.setEmail(param);
        }
        if ((param = updatedUser.getPassword()) != null) {
            oldUser.setPassword(param);
        }
        if ((param = updatedUser.getFirstName()) != null) {
            oldUser.setFirstName(param);
        }
        if ((param = updatedUser.getLastName()) != null) {
            oldUser.setLastName(param);
        }
        if ((param = updatedUser.getCountry()) != null) {
            oldUser.setCountry(param);
        }
        if ((param = updatedUser.getRole()) != null) {
            oldUser.setRole(param);
        }
        return true;
    }

    @Override
    public boolean deleteUser(Long id) {
        User userToDelete = (User) session.get(User.class, id);
        if (userToDelete == null) {
            return false;
        }
        session.delete(userToDelete);
        return true;
    }

    @Override
    public User getUser(String email, String password) {
        Query query = session.createQuery("FROM User WHERE email=:e_mail and password=:pass");
        query.setParameter("e_mail", email);
        query.setParameter("pass", password);
        return (User) query.uniqueResult();
    }
}
