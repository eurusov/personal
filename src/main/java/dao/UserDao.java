package dao;

import model.User;
import util.DBException;

import java.util.List;

public interface UserDao {

    void addUser(User usr) throws DBException;

    User getUser(long id);

    List<User> getAllUsers();

    boolean updateUser(User user) throws DBException;

    boolean deleteUser(long id) throws DBException;

}
