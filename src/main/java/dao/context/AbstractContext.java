package dao.context;

public abstract class AbstractContext<T> implements DaoContext<T> {

    private T context;

    public AbstractContext(T context) {
        this.context = context;
    }

    @Override
    public T getContext() {
        return context;
    }
}
