package dao.creator;

import dao.UserDaoJdbc;
import dao.UserDao;
import dao.context.DaoContext;
import dao.context.JdbcConnection;
import service.DBException;

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
            DriverManager.registerDriver((Driver) Class.forName("com.mysql.cj.jdbc.Driver").newInstance());

            StringBuilder url = new StringBuilder();

            url.
                    append("jdbc:mysql://").        //db type
                    append("localhost:").           //host name
                    append("3306/").                //port
                    append("users_test?").          //db name
                    append("user=root&").           //login
                    append("password=msql74_&").    //password
                    append("serverTimezone=UTC");   //timezone

            System.out.println("URL: " + url + "\n");
            return DriverManager.getConnection(url.toString());

        } catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new DBException(e);
        }
    }
}
