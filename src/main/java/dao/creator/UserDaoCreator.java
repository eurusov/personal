package dao.creator;

import dao.UserDao;
import dao.context.DaoContext;
import service.DBException;

/**
 * Сущность, которая умеет создавать конкретные реализации {@link UserDao} и {@link DaoContext}.
 * <p></p>
 * @param <T> контекст, с которым работает конкретная реализация {@link UserDao}.
 *            <p>Для Hibernate - это Session, для Jdbc - Connection.
 */
public interface UserDaoCreator<T> {

    UserDao createDao(DaoContext<T> daoContext);

    /**
     * Создает и возвращает или {@code DaoContext<Session>} или {@code DaoContext<Connection>}.
     */
    DaoContext<T> createDaoContext() throws DBException;

    void close();
}
