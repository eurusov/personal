package dao.context;

import dao.UserDao;
import service.DBException;
import dao.context.transaction.DaoTransaction;
import service.UserService;

/**
 * Сущность, которая содержит контекст, с которым работает {@link UserDao} - (Session или Connection в зависимости от конкретной реализации).
 * <p> Это позволяет  слою User-сервиса ({@link UserService}), ничего не зная о конкретной реализации Dao создавать Session или Connection и работать с ними.
 * <p></p>
 *
 * @param <T> контекст, с которым работает конкретная реализация UserDao.
 *            <p>Для Hibernate - это Session, для Jdbc - Connection.
 */
public interface DaoContext<T> extends AutoCloseable {

    T getContext();

    void close() throws DBException;

    DaoTransaction beginTransaction();
}
