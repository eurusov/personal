package service;

import dao.UserDao;
import dao.context.DaoContext;
import dao.context.transaction.DaoTransaction;
import dao.creator.UserDaoCreator;
import model.User;

import java.util.List;

public class UserService {

    private static UserDaoCreator userDaoCreator;

    public UserService(UserDaoCreator userDaoCreator) {
        if (UserService.userDaoCreator == null) {
            UserService.userDaoCreator = userDaoCreator;
        }
    }

    @SuppressWarnings("unchecked")
    public Long addUser(User user) throws DBException {
        DaoTransaction tx = null;
        Long assignedId = null; // result
        try (DaoContext daoContext = userDaoCreator.createDaoContext()) {
            UserDao userDao = userDaoCreator.createDao(daoContext);
            tx = daoContext.beginTransaction();
            assignedId = userDao.addUser(user); // do work
            tx.commit();
        } catch (DBException e) {
            if (tx != null) {
                tx.rollback();
            }
        }
        return assignedId;
    }

    @SuppressWarnings("unchecked")
    public User getUser(Long id) throws DBException {
        try (DaoContext daoContext = userDaoCreator.createDaoContext()) {
            UserDao userDao = userDaoCreator.createDao(daoContext);
            return userDao.getUser(id);
        }
    }

    @SuppressWarnings("unchecked")
    public List<User> getAllUser() throws DBException {
        try (DaoContext daoContext = userDaoCreator.createDaoContext()) {
            UserDao userDao = userDaoCreator.createDao(daoContext);
            return userDao.getAllUser();
        }
    }

    @SuppressWarnings("unchecked")
    public boolean updateUser(User user) throws DBException {
        DaoTransaction tx = null;
        boolean wasUpdated = false; // result
        try (DaoContext daoContext = userDaoCreator.createDaoContext()) {
            UserDao userDao = userDaoCreator.createDao(daoContext);
            tx = daoContext.beginTransaction();
            wasUpdated = userDao.updateUser(user); // do work
            tx.commit();
        } catch (DBException e) {
            if (tx != null) {
                tx.rollback();
            }
        }
        return wasUpdated;
    }

    @SuppressWarnings("unchecked")
    public boolean deleteUser(Long id) throws DBException {
        DaoTransaction tx = null;
        boolean wasDeleted = false; // result
        try (DaoContext daoContext = userDaoCreator.createDaoContext()) {
            UserDao userDao = userDaoCreator.createDao(daoContext);
            tx = daoContext.beginTransaction();

            wasDeleted = userDao.deleteUser(id); // do work

            tx.commit();
        } catch (DBException e) {
            if (tx != null) {
                tx.rollback();
            }
        }
        return wasDeleted;
    }
}
