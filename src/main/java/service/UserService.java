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

    public Long addUser(User user) throws DBException {
        try (DaoContext daoContext = userDaoCreator.createDaoContext()) {
            UserDao userDao = userDaoCreator.createDao(daoContext);
            DaoTransaction tx = daoContext.beginTransaction();

            Long assignedId = userDao.addUser(user);

            tx.commit();
            return assignedId;
        }
    }

    public User getUser(Long id) throws DBException {
        try (DaoContext daoContext = userDaoCreator.createDaoContext()) {
            UserDao userDao = userDaoCreator.createDao(daoContext);
            return userDao.getUser(id);
        }
    }

    public List<User> getAllUser() throws DBException {
        try (DaoContext daoContext = userDaoCreator.createDaoContext()) {
            UserDao userDao = userDaoCreator.createDao(daoContext);
            return userDao.getAllUser();
        }
    }

    public boolean updateUser(User user) throws DBException {
        try (DaoContext daoContext = userDaoCreator.createDaoContext()) {
            UserDao userDao = userDaoCreator.createDao(daoContext);
            DaoTransaction tx = daoContext.beginTransaction();

            boolean updated = userDao.updateUser(user);

            tx.commit();
            return updated;
        }
    }

    public boolean deleteUser(Long id) throws DBException {
        try (DaoContext daoContext = userDaoCreator.createDaoContext()) {
            UserDao userDao = userDaoCreator.createDao(daoContext);
            DaoTransaction tx = daoContext.beginTransaction();

            boolean deleted = userDao.deleteUser(id);

            tx.commit();
            return deleted;
        }
    }
}
