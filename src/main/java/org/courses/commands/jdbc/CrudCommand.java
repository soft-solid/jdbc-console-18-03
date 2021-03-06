package org.courses.commands.jdbc;

import org.courses.DAO.DAO;
import org.courses.commands.Command;
import org.courses.commands.CommandFormatException;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class CrudCommand<TEntity, TKey> implements Command {
    protected String[] args;
    protected String verb;
    protected DAO<TEntity, TKey> dao;

    private Class<TEntity> entityType;

    protected CrudCommand(DAO<TEntity, TKey> dao, Class<TEntity> entityType) {
        this.dao = dao;
        this.entityType = entityType;
    }

    @Override
    public void parse(String[] args) {
        if (args.length > 0) {
            verb = args[0];
        } else {
            throw new CommandFormatException("Entity action is not specified");
        }
        this.args = args;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void execute() {
        if ("add".equals(verb))
            add();
        else if ("update".equals(verb))
            update();
        else if ("delete".equals(verb))
            delete();
        else if ("list".equals(verb))
            list();
    }

    public void add() {
        TEntity entity = null;
        try {
            entity = entityType.newInstance();
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        }

        readEntity(entity);
        dao.save(Arrays.asList(entity));
        print(entity);
    }

    public void delete() {
        String[] idArray;
        if (args.length > 1) {
            idArray = new String[args.length - 1];
            System.arraycopy(args, 1, idArray, 0, idArray.length);
        } else {
            throw new CommandFormatException("ID to delete is not specified");
        }
        try {
            delete(idArray);
        } catch (IllegalArgumentException e) {
            throw new CommandFormatException("Entity is not found with current ID.");
        }

    }

    protected void delete(String[] idArray) {
        ArrayList<TEntity> collection = new ArrayList<TEntity>(idArray.length);
        for(String id : idArray){
            TKey i = convertId(id);
            TEntity entity = dao.read(i);
            collection.add(entity);
        }
        dao.delete(collection);
    }

    public void update() {
        String id;
        if (args.length > 1) {
            id = args[1];
        } else {
            throw new CommandFormatException("ID to update is not specified");
        }
        try {
            update(id);
        } catch (NullPointerException e) {
            throw new CommandFormatException("Entity is not found with current ID.");
        }
    }

    protected void update(String id) {
        TKey i = convertId(id);
        TEntity entity = dao.read(i);
        readEntity(entity);
        dao.save(Arrays.asList(entity));
    }

    protected abstract void readEntity(TEntity entity);

    protected abstract TKey convertId(String id);

    public void list() {
        String filter;
        if (args.length > 1) {
            filter = args[1];
            list(filter);
        } else {
            listAll();
        }
    }

    protected void listAll() {
        for (TEntity entity : dao.readAll()) {
            print(entity);
        }
    }

    protected void list(String filter) {
        for (TEntity entity : dao.find(filter)) {
            print(entity);
        }
    }

    protected abstract void print(TEntity entity);
}
