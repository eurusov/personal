package dao.creator;

import dao.UserDao;
import dao.context.DaoContext;
import service.DBException;

/**
 * Объект, который умеет создавать UserDao и UserDaoContext.
 *
 * @param <T> контекст, с которым работает конкретная реализация UserDao.
 *            <p>Для Hibernate - это Session, для Jdbc - Connection.
 */
public interface UserDaoCreator<T> {

    UserDao createDao(DaoContext<T> daoContext);

    /**
     * Создает и возвращает или {@code DaoContext<Session>} или {@code daoContext<Connection>}.
     */
    DaoContext<T> createDaoContext() throws DBException;

    void close();
}
