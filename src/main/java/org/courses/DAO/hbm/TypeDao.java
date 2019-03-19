package org.courses.DAO.hbm;

import org.apache.commons.validator.routines.IntegerValidator;
import org.courses.domain.hbm.Type;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.Collection;

public class TypeDao extends BaseDao<Type, Integer> {
    private IntegerValidator Int32 = IntegerValidator.getInstance();

    public TypeDao(SessionFactory factory) {
        super(factory, Type.class);
    }

    @Override
    public Collection<Type> find(String filter) {
        Collection<Type> result = null;
        Session session = null;
        try {
            session = factory.openSession();
            result = session
                    .createQuery("from Type " +
                            "where id = :id " +
                            "or name like :filter")
                    .setParameter("id", Int32.validate(filter))
                    .setParameter("filter", String.format("%%%s%%", filter))
                    .list();
        }
        finally {
            if (null != session)
                session.close();
        }
        return result;
    }
}
