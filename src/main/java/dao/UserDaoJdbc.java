package dao;

import daoContext.DaoContext;
import model.User;
import util.DBException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJdbc implements UserDao, AutoCloseable {

    private DaoContext daoContext;

    public UserDaoJdbc(DaoContext daoContext) {
        this.daoContext = daoContext;
    }

    private Connection getConnection() {
        return (Connection) daoContext.getContext();
    }

    @Override
    public void addUser(User user) throws DBException {
        try (PreparedStatement stm = getConnection().prepareStatement(INSERT_USERS_SQL)) {
            stm.setString(1, user.getFirstName());
            stm.setString(2, user.getLastName());
            stm.setString(3, user.getEmail());
            stm.setString(4, user.getCountry());
            System.out.println(stm);
            stm.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
            throw new DBException(e);
        }
    }

    @Override
    public User getUser(long id) throws DBException {
        try (PreparedStatement stm = getConnection().prepareStatement(SELECT_USER_BY_ID)) {
            stm.setLong(1, id);
            System.out.println(stm);
            ResultSet rs = stm.executeQuery();

            // Process the ResultSet object.
            User user = null;
            if (rs.next()) {
                String firstName = rs.getString("first_name");
                String lastMame = rs.getString("last_name");
                String email = rs.getString("email");
                String country = rs.getString("country");
                user = new User(id, firstName, lastMame, email, country);
            }
            return user;
        } catch (SQLException e) {
            printSQLException(e);
            throw new DBException(e);
        }
    }

    @Override
    public List<User> getAllUsers() throws DBException {
        try (Statement stm = getConnection().createStatement()) {
            System.out.println(SELECT_ALL_USERS);
            ResultSet rs = stm.executeQuery(SELECT_ALL_USERS);

            // Process the ResultSet object.
            List<User> users = new ArrayList<>();
            while (rs.next()) {
                Long id = rs.getLong("id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String email = rs.getString("email");
                String country = rs.getString("country");
                users.add(new User(id, firstName, lastName, email, country));
            }
            return users;
        } catch (SQLException e) {
            printSQLException(e);
            throw new DBException(e);
        }
    }

    @Override
    public boolean updateUser(User user) throws DBException {
        try (PreparedStatement stm = getConnection().prepareStatement(UPDATE_USERS_SQL)) {
            stm.setString(1, user.getFirstName());
            stm.setString(2, user.getLastName());
            stm.setString(3, user.getEmail());
            stm.setString(4, user.getCountry());
            stm.setLong(5, user.getId());
            System.out.println(stm);
            return stm.executeUpdate() == 1;
        } catch (SQLException e) {
            printSQLException(e);
            throw new DBException(e);
        }
    }

    @Override
    public boolean deleteUser(long id) throws DBException {
        try (PreparedStatement stm = getConnection().prepareStatement(DELETE_USER_SQL)) {
            stm.setLong(1, id);
            System.out.println(stm);
            return stm.executeUpdate() == 1;
        } catch (SQLException e) {
            printSQLException(e);
            throw new DBException(e);
        }
    }

    private void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }

    private static final String INSERT_USERS_SQL
            = "INSERT INTO users (first_name, last_name, email, country) VALUES"
            + " (?, ?, ?, ?);";
    private static final String SELECT_USER_BY_ID
            = "select id, first_name, last_name, email, country from users where id =?";
    private static final String SELECT_ALL_USERS = "select * from users";
    private static final String DELETE_USER_SQL = "delete from users where id = ?;";
    private static final String UPDATE_USERS_SQL
            = "update users set first_name = ?, last_name = ?, email= ?, country =? where id = ?;";

    @Override
    public void close() throws Exception {
        getConnection().commit();
        getConnection().close();
    }
}
