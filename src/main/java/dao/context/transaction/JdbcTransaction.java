package dao.context.transaction;

import service.DBException;

import java.sql.Connection;
import java.sql.SQLException;

public class JdbcTransaction implements DaoTransaction {
    private Connection connection;

    public JdbcTransaction(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void commit() throws DBException {
        try {
            if (!connection.getAutoCommit()) {
                connection.commit();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException(e);
        }
    }

    @Override
    public void rollback() throws DBException {
        try {
            if (!connection.getAutoCommit() && !connection.isClosed()) {
                connection.rollback();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException(e);
        }
    }
}
