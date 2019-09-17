package dao;

import java.sql.Connection;

public class JdbcConnection implements DaoContext<Connection> {

    private Connection connection;

    public JdbcConnection(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Connection getContext() {
        return connection;
    }

    @Override
    public void close() throws Exception {
        connection.close();
    }
}
