package org.courses.DAO.hbm;

import org.apache.commons.validator.routines.IntegerValidator;
import org.courses.domain.hbm.Manufacture;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.Collection;

public class ManufactureDao extends BaseDao<Manufacture, Integer> {
    private IntegerValidator Int32 = IntegerValidator.getInstance();

    public ManufactureDao(SessionFactory factory) {
        super(factory, Manufacture.class);
    }

    @Override
    public Collection<Manufacture> find(String filter) {
        Collection<Manufacture> result = null;
        Session session = null;
        try {
            session = factory.openSession();
            result = session
                    .createQuery("from Manufacture " +
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
