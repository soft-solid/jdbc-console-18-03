package org.courses.commands.jdbc;


import org.courses.DAO.DAO;
import org.courses.domain.hbm.*;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

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
    protected void readEntity(Socks socks) {
//        System.out.print("\tcolour: ");
//        if (scanner.hasNext()) {
//            int color = scanner.nextInt();
//            socks.setColour(new Color(color));
//        }
//        System.out.print("\tsize: ");
//        if (scanner.hasNext()) {
//            double size = scanner.nextDouble();
//            socks.setSize(size);
//        }
//        System.out.print("\ttype: ");
//        if (scanner.hasNext()) {
//            int type = scanner.nextInt();
//            socks.setType(typeDao.read(type));
//        }
//        System.out.print("\tmanufacture: ");
//        if (scanner.hasNext()) {
//            int manufacture = scanner.nextInt();
//            socks.setManufacture(manufactureDao.read(manufacture));
//        }




        List<Composition> compositionList = socks.getComposition();
        List<Composition> newCompositionList = compositionList.subList(0,1);
        socks.setComposition(newCompositionList);
//        Composition c = compositionList.get(1);
        //compositionList.remove(i);
//        compositionDao.delete(c.getId());
//        System.out.println("\tcomposition: ");
//        int percentage = 0;
//
//
//        ///
//        if (compositionList.isEmpty())
//            CompositionInsert(socks,0);
//        else
//            CompositionUpdate(compositionList, socks);






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

        Composition c = null;

        while (percentage < 100) {

            if(compositionList.size() > i)
                c = compositionList.get(i);
            //else
                //TODO insert

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
            compositionDao.save(Arrays.asList(c));
            i++;
        }
        if (compositionList.size() > i) {
            for (; i < compositionList.size();i++)
            {
                c = compositionList.get(i);
                compositionList.remove(i);
                compositionDao.delete(c.getId());
            }
        }

    }

    private void CompositionInsert(Socks socks, int percentage){

        Composition c;
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
