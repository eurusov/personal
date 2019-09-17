package dao;

import org.hibernate.Session;

public class HibernateSession implements DaoContext<Session> {

    private Session session;

    public HibernateSession(Session session) {
        this.session = session;
    }

    @Override
    public Session getContext() {
        return session;
    }

    @Override
    public void close() throws Exception {
        session.close();
    }
}
