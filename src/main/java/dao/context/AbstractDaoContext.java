package dao.context;

/**
 * По идее можно было обойтись и без этого абстрактного класса, напрямую наследуя {@link DaoContext} в {@link HibernateSession} и {@link JdbcConnection},
 * <p> но раз уж там метод getContext() и конструктор делают одно и тоже, - решил вынести это сюда
 * <p></p>
 */
public abstract class AbstractDaoContext<T> implements DaoContext<T> {

    private T context;

    public AbstractDaoContext(T context) {
        this.context = context;
    }

    @Override
    public T getContext() {
        return context;
    }
}
