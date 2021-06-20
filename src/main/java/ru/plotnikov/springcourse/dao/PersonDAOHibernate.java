package ru.plotnikov.springcourse.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ru.plotnikov.springcourse.entity.Person;
import ru.plotnikov.springcourse.utils.HibernateUtil;
import ru.plotnikov.springcourse.utils.SessionUtil;

/**
 *
 *
 * @author mplotnikov
 * @since 20.06.2021
 */
@Component
public class PersonDAOHibernate implements DAO
{
    private final SessionUtil sessionUtil;

    @Autowired
    public PersonDAOHibernate(SessionUtil sessionUtil)
    {
        this.sessionUtil = sessionUtil;
    }

    @Override
    public List<Person> index()
    {
        sessionUtil.openTransactionSession();

        Session session = sessionUtil.getSession();
        List<Person> allPerson = session.createSQLQuery("SELECT * FROM Person").addEntity(Person.class).list();

        sessionUtil.closeTransactionSession();

        return allPerson;

    }

    @Override
    public Person show(int id)
    {
        Person person = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession())
        {
            NativeQuery query = session.createSQLQuery("SELECT * FROM Person WHERE id=:id").addEntity(Person.class);
            query.setParameter("id", id);
            person = (Person)query.uniqueResult();
        }
        return person;
    }

    @Override
    public void sava(Person person)
    {
        try (Session session = HibernateUtil.getSessionFactory().openSession())
        {
            session.save(person);
        }
    }

    @Override
    public void update(int id, Person person)
    {
        try (Session session = HibernateUtil.getSessionFactory().openSession())
        {
            session.update(person);
        }
    }

    @Override
    public void delete(int id)
    {
        try (Session session = HibernateUtil.getSessionFactory().openSession())
        {
            session.delete(show(id));
        }
    }
}
