package ru.plotnikov.springcourse.utils;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

/**
 *
 *
 * @author mplotnikov
 * @since 25.06.2021
 */
@Component
public class SessionUtil
{
    private Session session;
    private Transaction transaction;

    public SessionUtil()
    {
    }

    public Session getSession()
    {
        return session;
    }

    public Transaction getTransaction()
    {
        return transaction;
    }

    private Session openSession()
    {
        return HibernateUtil.getSessionFactory().openSession();
    }

    public Session openTransactionSession()
    {
        this.session = openSession();
        this.transaction = this.session.beginTransaction();
        return session;
    }

    private void closeSession()
    {
        session.close();
    }

    public void closeTransactionSession()
    {
        this.transaction.commit();
        closeSession();
    }
}
