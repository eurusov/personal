package dao.creator;

import dao.context.DaoContext;
import service.DaoType;

import java.util.function.Supplier;

/**
 * Сущность которая является поставщиком DaoContext: содержит DriverManager для JDBC, или SessionFactory для Hibernate.
 * Метод возвращает: JdbcConnection или HibernateSession соответственно.
 */
public interface UserDaoCreator<T> extends Supplier<DaoContext<T>> {

    static DaoType daoType = DaoType.JDBC;

    DaoContext<T> getDaoContext();
}
