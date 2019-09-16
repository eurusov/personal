package dao;

import model.User;
import util.DBException;

import java.sql.SQLException;
import java.util.List;

public interface UserDao extends AutoCloseable{

    void addUser(User usr) throws DBException;

    User getUser(long id);

    List<User> getAllUsers() throws SQLException;

    boolean updateUser(User user) throws DBException;

    boolean deleteUser(long id) throws DBException;

}
