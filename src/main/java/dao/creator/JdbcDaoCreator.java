package dao.creator;

import dao.context.DaoContext;
import dao.context.JdbcConnection;
import util.StringConst;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcDaoCreator implements UserDaoCreator<Connection> {

    static {
        try {
            Class.forName(StringConst.JDBC_DRIVER_NAME);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public DaoContext<Connection> getDaoContext() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(StringConst.JDBC_URL, StringConst.USERNAME, StringConst.PASSWORD);
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new JdbcConnection(connection);
    }

    @Override
    public DaoContext<Connection> get() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(StringConst.JDBC_URL, StringConst.USERNAME, StringConst.PASSWORD);
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new JdbcConnection(connection);
    }
}
