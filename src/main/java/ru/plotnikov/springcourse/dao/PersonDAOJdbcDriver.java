package ru.plotnikov.springcourse.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ru.plotnikov.springcourse.entity.Person;
import ru.plotnikov.springcourse.utils.JdbcUtil;

/**
 * Реализация DAO через JDBC Driver
 *
 * @author mplotnikov
 * @since 15.06.2021
 */
@Component
public class PersonDAOJdbcDriver implements DAO
{
    private final JdbcUtil jdbcUtil;

    @Autowired
    public PersonDAOJdbcDriver(JdbcUtil jdbcUtil)
    {
        this.jdbcUtil = jdbcUtil;
    }

    /**
     * возвращает всех людей
     */
    public List<Person> index()
    {
        final List<Person> personList = new ArrayList<>();
        try (Statement statement = jdbcUtil.getConnection().createStatement();)
        {
            String query = "SELECT * FROM person;";
            ResultSet result = statement.executeQuery(query);

            Person person;
            while (result.next())
            {
                person = new Person();
                person.setId(result.getInt("id"));
                person.setName(result.getString("name"));
                person.setAge(result.getInt("age"));
                person.setEmail(result.getString("email"));

                personList.add(person);
            }
        }
        catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }

        return personList;
    }

    //возвращает человека по индексу
    public Person show(int id)
    {
        Person person = null;
        try (PreparedStatement preparedStatement = jdbcUtil.getConnection().prepareStatement("SELECT * FROM Person WHERE id = ?"))
        {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            //сдвигаем указатель на первую строчку в ответе от БД
            resultSet.next();
            //затем получаем данные из первой строки
            person = new Person();
            person.setId(resultSet.getInt("id"));
            person.setName(resultSet.getString("name"));
            person.setAge(resultSet.getInt("age"));
            person.setEmail(resultSet.getString("email"));
        }
        catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }
        return person;
    }

    public void sava(Person person)
    {
        try (PreparedStatement preparedStatement = jdbcUtil.getConnection().prepareStatement("INSERT INTO person VALUES (1, ?, ?, ?)"))
        {
            preparedStatement.setString(1, person.getName());
            preparedStatement.setInt(2, person.getAge());
            preparedStatement.setString(3, person.getEmail());

            preparedStatement.executeUpdate();
        }
        catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }
    }

    public void update(int id, Person person)
    {
        try (PreparedStatement preparedStatement = jdbcUtil.getConnection().prepareStatement("UPDATE Person SET name=?, "
                + "age=?, email=? WHERE id=?"))
        {
            preparedStatement.setString(1, person.getName());
            preparedStatement.setInt(2, person.getAge());
            preparedStatement.setString(3, person.getEmail());
            preparedStatement.setInt(4, id);

            preparedStatement.executeUpdate();
        }
        catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }
    }

    public void delete(int id)
    {
        try (PreparedStatement preparedStatement = jdbcUtil.getConnection().prepareStatement("DELETE FROM Person WHERE id=?"))
        {
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
        }
        catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }
    }
}
