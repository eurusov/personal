package util;

public class StringConst {
    public static final String CONFIG = "WEB-INF/config.properties";

    public static final String JDBC_DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
    public static final String JDBC_URL = "jdbc:mysql://localhost:3306/usersdb?serverTimezone=UTC";
    public static final String USERNAME = "root";
    public static final String PASSWORD = "msql74_";

    public static final String INSERT_USERS_SQL
            = "INSERT INTO users (first_name, last_name, email, country) VALUES"
            + " (?, ?, ?, ?);";
    public static final String SELECT_USER_BY_ID
            = "select id, first_name, last_name, email, country from users where id =?";
    public static final String SELECT_ALL_USERS = "select * from users";
    public static final String DELETE_USER_SQL = "delete from users where id = ?;";
    public static final String UPDATE_USERS_SQL
            = "update users set first_name = ?, last_name = ?, email= ?, country =? where id = ?;";
}
