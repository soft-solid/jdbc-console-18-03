package org.courses.DAO.hbm;

import org.apache.commons.validator.routines.IntegerValidator;
import org.courses.domain.hbm.Material;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.Collection;

public class MaterialDao extends BaseDao<Material, Integer> {
    private IntegerValidator Int32 = IntegerValidator.getInstance();

    public MaterialDao(SessionFactory factory) {
        super(factory, Material.class);
    }

    @Override
    public Collection<Material> find(String filter) {
        Collection<Material> result = null;
        Session session = null;
        try {
            session = factory.openSession();
            result = session
                    .createQuery("from Material " +
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