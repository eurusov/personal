package service;

public class DBException extends RuntimeException {
    public DBException(Throwable throwable) {
        super(throwable);
    }
}
