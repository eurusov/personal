package dao;

import com.sun.istack.internal.Nullable;
import model.User;
import service.DBException;
import util.StringConst;

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
        try (PreparedStatement stmt = connection.prepareStatement(StringConst.SQL_ADD);
             PreparedStatement stmt2 = connection.prepareStatement(StringConst.SQL_GET_ID_BY_EMAIL)
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
        return getUserBySqlQuery(StringConst.SQL_GET_BY_ID, id.toString());
    }

    @Override
    public List<User> getAllUser() throws DBException {
        try (Statement stmt = connection.createStatement()) {
            ResultSet result = stmt.executeQuery(StringConst.SQL_SELECT_ALL);
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
        try (PreparedStatement stmt = connection.prepareStatement(StringConst.SQL_UPDATE)) {
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
        try (PreparedStatement stmt = connection.prepareStatement(StringConst.SQL_DEL_BY_ID)) {
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
}
