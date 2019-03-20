package org.courses.commands.jdbc;

import org.courses.DAO.DAO;
import org.courses.domain.hbm.Manufacture;

import java.util.Scanner;

public class ManufactureCommand extends CrudCommand<Manufacture, Integer> {
    private Scanner scanner;

    public ManufactureCommand(DAO<Manufacture, Integer> dao, Scanner scanner) {
        super(dao, Manufacture.class);
        this.scanner = scanner;
    }

    @Override
    protected void readEntity(Manufacture manufacture) {
        System.out.print("name: ");
        if (scanner.hasNext()) {
            String name = scanner.nextLine();
            manufacture.setName(name);
        }
    }

    @Override
    protected Integer convertId(String id) {
        return Integer.parseInt(id);
    }

    @Override
    protected void print(Manufacture m) {
        System.out.println(String.format("Manufacture { id: %d, name: %s }", m.getId(), m.getName()));
    }
}
