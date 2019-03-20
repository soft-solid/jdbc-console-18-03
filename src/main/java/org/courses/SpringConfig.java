package org.courses;

import org.courses.DAO.DAO;
import org.courses.DAO.hbm.ManufactureDao;
import org.courses.DAO.hbm.MaterialDao;
import org.courses.DAO.hbm.TypeDao;
import org.courses.commands.Command;
import org.courses.commands.jdbc.CrudCommand;
import org.courses.commands.jdbc.ManufactureCommand;
import org.courses.commands.jdbc.MaterialCommand;
import org.courses.commands.jdbc.TypeCommand;
import org.courses.domain.hbm.*;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;

@Configuration
@EnableTransactionManagement(proxyTargetClass = true)
public class SpringConfig {
    private final Properties hibernateProperties() {
        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("dialect", "org.hibernate.dialect.SQLiteDialect");
        hibernateProperties.setProperty("connection.pool_size", "1");
        hibernateProperties.setProperty("show_sql", "true");
        hibernateProperties.setProperty("format_sql", "true");
        hibernateProperties.setProperty("hibernate.jdbc.batch_size", "30");

        return hibernateProperties;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new HibernateTransactionManager(sessionFactory);
    }

    @Bean
    public DriverManagerDataSource configurableDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.sqlite.JDBC");
        dataSource.setUrl("jdbc:sqlite:D:\\1dotNet\\Projects\\JdbcConcole 18 03\\CourseDB.db");
        return dataSource;
    }

    @Bean
    public DataSource dataSource() {
        return configurableDataSource();
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setHibernateProperties(hibernateProperties());
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setAnnotatedClasses(
                Manufacture.class,
                Material.class,
                Type.class,
                Socks.class,
                Composition.class,
                Storage.class);
        return sessionFactory;
    }

    SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory factory) {
        this.sessionFactory = factory;
    }

    @Bean
    public DAO<Type, Integer> typeDao() {
        return new TypeDao(sessionFactory);
    }

    @Bean
    public DAO<Material, Integer> materialDao() {
        return new MaterialDao(sessionFactory);
    }

    @Bean
    public DAO<Manufacture, Integer> manufactureDao() {
        return new ManufactureDao(sessionFactory);
    }

    @Bean
    public Scanner scanner() {
        return new Scanner(System.in);
    }

    @Bean
    public CrudCommand<Material, Integer> materialCommand() {
        return new MaterialCommand(materialDao(), scanner());
    }

    @Bean
    public CrudCommand<Type, Integer> typeCommand() {
        return new TypeCommand(typeDao(), scanner());
    }

    @Bean
    public CrudCommand<Manufacture, Integer> manufactureCommand() {
        return new ManufactureCommand(manufactureDao(), scanner());
    }

    @Bean
    public Map<String, Command> commands() {
        Map<String, Command> commands = new HashMap<>();

        commands.put("material", materialCommand());
        commands.put("type", typeCommand());
        commands.put("manufacture", manufactureCommand());

        return commands;
    }
}
