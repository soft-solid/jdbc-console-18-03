package org.courses.commands.jdbc;


import org.courses.DAO.DAO;
import org.courses.domain.hbm.*;

import java.awt.*;
import java.util.*;
import java.util.List;

public class SocksCommand extends CrudCommand<Socks, Integer> {
    private Scanner scanner;
    DAO<Type, Integer> typeDao;
    DAO<Material, Integer> materialDao;
    DAO<Manufacture, Integer> manufactureDao;
    DAO<Composition, Integer> compositionDao;

    public SocksCommand(DAO<Socks, Integer> dao,
                        DAO<Type, Integer> typeDao,
                        DAO<Material, Integer> materialDao,
                        DAO<Manufacture, Integer> manufactureDao,
                        DAO<Composition, Integer> compositionDao,
                        Scanner scanner) {
        super(dao, Socks.class);
        this.scanner = scanner;
        this.typeDao = typeDao;
        this.materialDao = materialDao;
        this.manufactureDao = manufactureDao;
        this.compositionDao = compositionDao;
    }

    @Override
    protected void update(String id) {
        int i = convertId(id);
        Socks entity = dao.read(i);
        readEntity(entity);
    }

    @Override
    protected void readEntity(Socks socks) {
        System.out.print("\tcolour: ");
        if (scanner.hasNext()) {
            int color = scanner.nextInt();
            socks.setColour(new Color(color));
        }
        System.out.print("\tsize: ");
        if (scanner.hasNext()) {
            double size = scanner.nextDouble();
            socks.setSize(size);
        }
        System.out.print("\ttype: ");
        if (scanner.hasNext()) {
            int type = scanner.nextInt();
            socks.setType(typeDao.read(type));
        }
        System.out.print("\tmanufacture: ");
        if (scanner.hasNext()) {
            int manufacture = scanner.nextInt();
            socks.setManufacture(manufactureDao.read(manufacture));
        }

        List<Composition> compositionList = socks.getComposition();
        //List<Composition> newCompositionList = compositionList.subList(0,1);
        //compositionList.remove(1);

//        Composition c = compositionList.get(1);
        //compositionList.remove(i);
//        compositionDao.delete(c.getId());
//        System.out.println("\tcomposition: ");
//        int percentage = 0;
//
//
        ///
        if (compositionList.isEmpty())
            CompositionInsert(socks);
        else
            CompositionUpdate(compositionList, socks);

//        Composition c;
//        int i = 0;
//        while (percentage < 100) {
//            if (compositionList.isEmpty())
//                c = new Composition();
//            else
//                c = compositionList.get(i);
//
//            System.out.print("\tmaterial: ");
//            if (scanner.hasNext()) {
//                int id = scanner.nextInt();
//                c.setMaterial(materialDao.read(id));
//            }
//            System.out.print("\tpercantage: ");
//            if (scanner.hasNext()) {
//                int percents = scanner.nextInt();
//                c.setPercentage(percents);
//                percentage += percents;
//                i++;
//            }
//
//            socks.add(c);

    }

    private void CompositionUpdate(List<Composition> compositionList, Socks socks){
        int percentage = 0;
        int i = 0;
        int compositionCount = compositionList.size();
        Composition c = null;

        while (percentage < 100) {

            if(compositionCount > i)
                c = compositionList.get(i);
            else{
                c = new Composition();
                socks.add(c);
            }

            System.out.print("\tmaterial: ");
            if (scanner.hasNext()) {
                int id = scanner.nextInt();
                c.setMaterial(materialDao.read(id));
            }
            System.out.print("\tpercantage: ");
            if (scanner.hasNext()) {
                int percents = scanner.nextInt();
                c.setPercentage(percents);
                percentage += percents;
            }
            //compositionDao.save(Arrays.asList(c));
            i++;
        }
        if (compositionCount > i){
            List<Composition> toDelete = compositionList.subList(i,compositionCount);
            List<Composition> toDel = new ArrayList<Composition>(toDelete);
            //Collections.copy(toDel, toDelete);

            compositionList.removeAll(toDelete);
            dao.save(Arrays.asList(socks));
            compositionDao.delete(toDel);
        }
        else {
            dao.save(Arrays.asList(socks));
        }
//        if (compositionCount > i) {
//            for (; i < compositionCount;i++)
//            {
//                compositionList.remove(i);
//
//                c = compositionList.get(i);
//                compositionDao.delete(c.getId());
//            }
//        }
    }

    private void CompositionInsert(Socks socks){

        Composition c;
        int percentage = 0;
        while (percentage < 100) {

            c = new Composition();

            System.out.print("\tmaterial: ");
            if (scanner.hasNext()) {
                int id = scanner.nextInt();
                c.setMaterial(materialDao.read(id));
            }
            System.out.print("\tpercantage: ");
            if (scanner.hasNext()) {
                int percents = scanner.nextInt();
                c.setPercentage(percents);
                percentage += percents;
            }
            socks.add(c);
        }
        dao.save(Arrays.asList(socks));
    }

    @Override
    protected Integer convertId(String id) {
        return Integer.parseInt(id);
    }

    @Override
    protected void print(Socks socks) {
        System.out.println(socks);
    }
}
