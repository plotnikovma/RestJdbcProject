package ru.plotnikov.springcourse.utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Component;

import ru.plotnikov.springcourse.entity.Person;

/**
 * Утилитный класс получения Hibernate сессии
 *
 * @author mplotnikov
 * @since 20.06.2021
 */
@Component
public class HibernateUtil
{
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory()
    {
        if (sessionFactory == null)
        {
            try
            {
                Configuration configuration = new Configuration().configure();
                configuration.addAnnotatedClass(Person.class);
                return configuration.buildSessionFactory();
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new ExceptionInInitializerError();
            }
        }
        return sessionFactory;
    }
}
