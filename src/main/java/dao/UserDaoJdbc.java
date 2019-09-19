package dao;

import com.sun.istack.internal.Nullable;
import model.User;
import service.DBException;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserDaoJdbc implements UserDao {

    private Connection connection;

    public UserDaoJdbc(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Long addUser(User user) throws DBException {
        try (PreparedStatement stmt = connection.prepareStatement(SQL_ADD);
             PreparedStatement stmt2 = connection.prepareStatement(SQL_GET_ID_BY_EMAIL)
        ) {
            int idx = 1;
            stmt.setString(idx++, user.getFirstName());
            stmt.setString(idx++, user.getLastName());
            stmt.setString(idx++, user.getEmail());
            stmt.setString(idx++, user.getCountry());
            stmt.executeUpdate();
            stmt2.setString(1, user.getEmail());
            ResultSet resultSet = stmt2.executeQuery();
            if (resultSet.next()) {
                return resultSet.getLong(1);
            }
            return null;
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    @Override
    public User getUser(Long id) throws DBException {
        return getUserBySqlQuery(SQL_GET_BY_ID, id.toString());
    }

    @Override
    public List<User> getAllUser() throws DBException {
        try (Statement stmt = connection.createStatement()) {
            ResultSet result = stmt.executeQuery(SQL_SELECT_ALL);
            List<User> clientsList = new ArrayList<>();
            while (result.next()) {
                User user = new User(
                        result.getLong("id"),
                        result.getString("first_name"),
                        result.getString("last_name"),
                        result.getString("email"),
                        result.getString("country")
                );
                clientsList.add(user);
            }
            return (clientsList.isEmpty()) ? Collections.emptyList() : clientsList;
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    @Override
    public boolean updateUser(User user) throws DBException {
        try (PreparedStatement stmt = connection.prepareStatement(SQL_UPDATE)) {
            int idx = 1;
            stmt.setString(idx++, user.getFirstName());
            stmt.setString(idx++, user.getLastName());
            stmt.setString(idx++, user.getEmail());
            stmt.setString(idx++, user.getCountry());
            stmt.setLong(idx++, user.getId());
            int updatedRows = stmt.executeUpdate();
            return updatedRows == 1;
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    @Override
    public boolean deleteUser(Long id) throws DBException {
        try (PreparedStatement stmt = connection.prepareStatement(SQL_DEL_BY_ID)) {
            stmt.setLong(1, id);
            int updatedRows = stmt.executeUpdate();
            return updatedRows == 1;
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    /**
     * Вспомогательный метод.<p>Выполняет SQL запрос с параметрами,
     * и если результат содержит хотя бы один объект User,
     * то возвращает первого, иначе - <code>null</code>.
     * <p>В логике данной программы предполагается, что корректный запрос для данного метода
     * должен возвращать результат состоящий только из одной записи или пустой результат.
     * <p>В противном случае возвращается <code>User</code> соответствующий первой записи из <code>ResultSet</code>.
     *
     * @param sql  строка содержащая SQL запрос
     * @param args подстановочные параметры для запроса
     * @return объект <code>User</code>, найденный в таблице по данному SQL запросу,
     * или <code>null</code> если результат запроса пуст
     */
    private @Nullable
    User getUserBySqlQuery(final String sql, final String... args)
            throws DBException {
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            for (int i = 0; i < args.length; i++) {
                stmt.setString(i + 1, args[i]);
            }
            ResultSet resultSet = stmt.executeQuery();
            if (!resultSet.next()) {
                return null;
            }
            return new User(
                    resultSet.getLong("id"),
                    resultSet.getString("first_name"),
                    resultSet.getString("last_name"),
                    resultSet.getString("email"),
                    resultSet.getString("country")
            );
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    private static final String TABLE_NAME = "users";
    private static final String SQL_GET_ID_BY_EMAIL = "SELECT id FROM " + TABLE_NAME + " WHERE email=?";
    private static final String SQL_ADD = "INSERT INTO " + TABLE_NAME + " (first_name, last_name, email, country) values (?, ?, ?, ?)";
    private static final String SQL_GET_BY_ID = "SELECT * FROM " + TABLE_NAME + " WHERE id=?";
    private static final String SQL_DEL_BY_ID = "DELETE FROM " + TABLE_NAME + " WHERE id=?";
    private static final String SQL_UPDATE = "UPDATE " + TABLE_NAME + " SET first_name=?, last_name=?, email=?, country=? WHERE id=?";
    private static final String SQL_SELECT_ALL = "SELECT * FROM " + TABLE_NAME;
}
