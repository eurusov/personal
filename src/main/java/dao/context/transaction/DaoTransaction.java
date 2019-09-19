package dao.context.transaction;

import service.DBException;

public interface DaoTransaction {
    void commit() throws DBException;
}
