package dao.context;

import dao.context.transaction.JdbcTransaction;
import service.DBException;
import dao.context.transaction.DaoTransaction;

import java.sql.Connection;
import java.sql.SQLException;

public class JdbcConnection extends AbstractDaoContext<Connection> {

    public JdbcConnection(Connection connection) {
        super(connection);
    }

    @Override
    public DaoTransaction beginTransaction() {
        return new JdbcTransaction(super.getContext());
    }

    @Override
    public void close() throws DBException {
        try {
            Connection connection = super.getContext();
            if (!connection.getAutoCommit()) {
                connection.commit();
            }
            /* Необязательно закрывать здесь коннекшн, так как если он будет закрыт (здесь, или по таймауту автоматически),
            то при создании нового DaoContext, будет открыт новый. (см. dao.creator.JdbcDaoCreator.createDaoContext())
            Чтобы избежать постоянного закрытия-открытия коннекшена, уберем здесь connection.close()
             */
//            connection.close();
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }
}
