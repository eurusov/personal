package dao.creator;

import dao.UserDaoJdbc;
import dao.UserDao;
import dao.context.DaoContext;
import dao.context.JdbcConnection;
import service.DBException;
import util.DBService;

import java.sql.Connection;
import java.sql.SQLException;

public class JdbcDaoCreator implements UserDaoCreator<Connection> {

    private static Connection connection;

    public JdbcDaoCreator() throws DBException {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DBService.getMysqlConnection();
            }
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    @Override
    public UserDao createDao(DaoContext<Connection> daoContext) {
        return new UserDaoJdbc(daoContext.getContext());
    }

    @Override
    public DaoContext<Connection> createDaoContext() throws DBException {
        try {
            if (connection.isClosed()) {
                connection = DBService.getMysqlConnection();
            }
            return new JdbcConnection(connection);
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    @Override
    public void close() {

    }

}
