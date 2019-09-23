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

    @Override
    public boolean updateUser(User updatedUser) {
        User existingUser = (User) session.get(User.class, updatedUser.getId());
        if (existingUser == null) {
            return false;
        }
        existingUser.setFirstName(updatedUser.getFirstName());
        existingUser.setLastName(updatedUser.getLastName());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setCountry(updatedUser.getCountry());
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
    public User getUser(String email, String password) throws DBException {
        return null;
    }
}
