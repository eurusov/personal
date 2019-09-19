package dao.creator;

import dao.UserDaoJdbc;
import dao.UserDao;
import dao.context.DaoContext;
import dao.context.JdbcConnection;
import service.DBException;
import util.StringConst;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcDaoCreator implements UserDaoCreator<Connection> {

    private static Connection connection;

    public JdbcDaoCreator() throws DBException {
        try {
            if (connection == null || connection.isClosed()) {
                connection = getMysqlConnection();
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
                connection = getMysqlConnection();
            }
            return new JdbcConnection(connection);
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    @Override
    public void close() {

    }

    private static Connection getMysqlConnection() throws DBException {
        try {
            DriverManager.registerDriver((Driver) Class.forName(StringConst.JDBC_DRIVER_NAME).newInstance());
            return DriverManager.getConnection(StringConst.JDBC_URL, StringConst.USERNAME, StringConst.PASSWORD);
        } catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new DBException(e);
        }
    }
}
