package org.courses.DAO.hbm;

import org.apache.commons.validator.routines.IntegerValidator;
import org.courses.domain.hbm.Socks;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.Collection;

public class SocksDao extends BaseDao<Socks, Integer> {
    private IntegerValidator Int32 = IntegerValidator.getInstance();


    public SocksDao(SessionFactory factory) {
        super(factory, Socks.class);
    }

    @Override
    public Collection<Socks> find(String filter) {
        Session session = factory.openSession();
        return session
                .createQuery("from Socks " +
                        "where id = :id ")
                .setParameter("id", Int32.validate(filter))
                .list();
    }
}
