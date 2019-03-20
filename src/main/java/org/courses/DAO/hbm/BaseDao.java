package org.courses.DAO.hbm;

import org.courses.DAO.DAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.Collection;

public abstract class BaseDao<TEntity, TKey> implements DAO<TEntity, TKey> {
    protected Class<TEntity> entityType;
    protected SessionFactory factory;

    protected BaseDao(SessionFactory factory, Class<TEntity> entityType) {
        this.entityType = entityType;
        this.factory = factory;
    }

    @Override
    public void save(Collection<TEntity> entity) {
        Session session = factory.getCurrentSession();
        saveEntities(session, entity);
    }

    private void saveEntities(Session session, Collection<TEntity> entities) {
        for (TEntity entity : entities) {
            session.save(entity);
        }
    }

    @Override
    public TEntity read(TKey id) {
        Session session = factory.getCurrentSession();
        return session.find(entityType, id);
    }

    @Override
    public Collection<TEntity> readAll() {
        String query = String.format("from %s", entityType.getName());
        Session session = factory.getCurrentSession();
        return session
                .createQuery(query)
                .list();
    }

    @Override
    public abstract Collection<TEntity> find(String filter);

    @Override
    public void delete(TKey id) {
        Session session = factory.getCurrentSession();
        TEntity entity = session.find(entityType, id);
        session.delete(entity);
    }
}
