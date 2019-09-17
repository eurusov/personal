package daoContext.producer;

import daoContext.DaoContext;
import util.DaoType;

/**
 * Сущность которая является поставщиком DaoContext: содержит DriverManager для JDBC, или SessionFactory для Hibernate.
 * Метод возвращает: JdbcConnection или HibernateSession соответственно.
 */
public interface DaoContextProducer {

    static DaoType daoType = DaoType.JDBC;

    DaoContext getDaoContext();
}
