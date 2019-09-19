package dao.context;

import service.DBException;
import dao.context.transaction.DaoTransaction;

/**
 * Сущность, которая содержит контекст, с которым работает UserDao.
 *
 * @param <T> контекст, с которым работает конкретная реализация UserDao.
 *            <p>Для Hibernate - это Session, для Jdbc - Connection.
 */
public interface DaoContext<T> extends AutoCloseable {

    T getContext();

    void close() throws DBException;

    DaoTransaction beginTransaction();
}
