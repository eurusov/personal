package daoContext.producer;

import daoContext.DaoContext;
import daoContext.JdbcConnection;
import util.StringConst;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionProducer implements DaoContextProducer {

    static {
        try {
            Class.forName(StringConst.JDBC_DRIVER_NAME);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public DaoContext getDaoContext() {
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
