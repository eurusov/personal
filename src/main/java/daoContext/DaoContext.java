package daoContext;

/**
 * Обертка. Сущность которая содержит контекст с которым работает DAO: Connection для JDBC, или Session для Hibernate.
 */
public interface DaoContext<T> extends AutoCloseable {

    T getContext();
}
