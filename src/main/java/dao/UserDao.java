package dao;

import model.User;
import service.DBException;

import java.sql.SQLException;
import java.util.List;

public interface UserDao extends AutoCloseable {

    // create
    void addUser(User usr) throws DBException;

    // read
    User getUser(long id) throws DBException;

    List<User> getAllUsers() throws SQLException, DBException;

    // update
    boolean updateUser(User user) throws DBException;

    // delete
    boolean deleteUser(long id) throws DBException;
}
