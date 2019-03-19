package org.courses.DAO.hbm;

import org.courses.DAO.DAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

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
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            saveEntities(session, transaction, entity);
        }
        catch (Exception e) {
            if (null != transaction)
                transaction.rollback();
            throw e;
        }
        finally {
            if (null != session)
                session.close();
        }
    }

    private void saveEntities(Session session, Transaction transaction, Collection<TEntity> entities) {
        for (TEntity entity : entities) {
            session.saveOrUpdate(entity);
        }
        transaction.commit();
    }

    @Override
    public TEntity read(TKey id) {
        TEntity result = null;
        Session session = null;
        try {
            session = factory.openSession();
            result = session.find(entityType, id);
        }
        finally {
            if (null != session)
                session.close();
        }
        return result;
    }

    @Override
    public Collection<TEntity> readAll() {
        Collection<TEntity> result = null;
        Session session = null;
        try {
            String query = String.format("from %s", entityType.getName());
            session = factory.openSession();
            result = session
                    .createQuery(query)
                    .list();
        }
        finally {
            if (null != session)
                session.close();
        }
        return result;
    }

    @Override
    public abstract Collection<TEntity> find(String filter);

    @Override
    public void delete(TKey id) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            TEntity entity = session.find(entityType, id);
            session.delete(entity);
            transaction.commit();
        }
        catch (Exception e) {
            if (null != transaction)
                transaction.rollback();
            throw e;
        }
        finally {
            if (null != session)
                session.close();
        }

    }
}
