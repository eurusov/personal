package util;

public abstract class StringConst {

    public static final String CONFIG_PATH = "WEB-INF/classes/config.properties";

    public static final String JDBC_DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
    public static final String JDBC_URL = "jdbc:mysql://localhost:3306/usersdb?serverTimezone=UTC";
    public static final String USERNAME = "root";
    public static final String PASSWORD = "msql74_";

    /* SQL Queries Strings */
    public static final String TABLE_NAME = "users";
    public static final String SQL_GET_ID_BY_EMAIL = "SELECT id FROM " + TABLE_NAME + " WHERE email=?";
    public static final String SQL_INSERT = "INSERT INTO " + TABLE_NAME + " (first_name, last_name, email, country) values (?, ?, ?, ?)";
    public static final String SQL_GET_BY_ID = "SELECT * FROM " + TABLE_NAME + " WHERE id=?";
    public static final String SQL_DEL_BY_ID = "DELETE FROM " + TABLE_NAME + " WHERE id=?";
    public static final String SQL_UPDATE = "UPDATE " + TABLE_NAME + " SET first_name=?, last_name=?, email=?, country=? WHERE id=?";
    public static final String SQL_SELECT_ALL = "SELECT * FROM " + TABLE_NAME;
}
