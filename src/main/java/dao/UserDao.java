package dao;

import model.User;
import service.DBException;

import java.util.List;

public interface UserDao {

    // create
    Long addUser(User user) throws DBException;

    // read
    User getUser(Long id) throws DBException;

    List<User> getAllUser() throws DBException;

    // update
    boolean updateUser(User user) throws DBException;

    // delete
    boolean deleteUser(Long id) throws DBException;

    User getUser(String email, String password) throws DBException;
}
