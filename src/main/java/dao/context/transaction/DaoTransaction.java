package dao.context.transaction;

import dao.context.DaoContext;
import service.DBException;
import service.UserService;

/**
 * Этот интерфейс абстрагирует понятие транзакции для контекста с которым работает Dao ({@link DaoContext}).
 * <p> Это позволяет работать с транзакциями из слоя User-сервиса ({@link UserService}), ничего не зная о конкретной реализации Dao.
 * <p></p> Транзакция создается и открывается при создании в некоторой конкретной реализации {@link dao.context.DaoContext#beginTransaction()} и закрывается
 * методом {@code commit()} данного интерфейса.
 * <p></p>
 * <p> По хорошему сюда надо добавить еще метод {@code rollback()}
 */
public interface DaoTransaction {
    void commit() throws DBException;
}
