package org.courses.DAO.hbm;

import org.courses.domain.hbm.Composition;
import org.hibernate.SessionFactory;

import java.util.Collection;

public class CompositionDao extends BaseDao<Composition, Integer> {

    public CompositionDao(SessionFactory factory) {
        super(factory, Composition.class);
    }

    @Override
    public Collection<Composition> find(String filter) {
        throw new UnsupportedOperationException();
        //return null;
    }
}
