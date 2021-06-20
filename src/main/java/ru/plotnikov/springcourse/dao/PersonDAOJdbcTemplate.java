package ru.plotnikov.springcourse.dao;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;

import ru.plotnikov.springcourse.entity.Person;
import ru.plotnikov.springcourse.utils.ConnectionUtils;

/**
 *
 *
 * @author mplotnikov
 * @since 17.06.2021
 */
public class PersonDAOJdbcTemplate implements DAO
{
    private final ConnectionUtils connectionUtils;

    public PersonDAOJdbcTemplate(ConnectionUtils connectionUtils)
    {
        this.connectionUtils = connectionUtils;
    }

    /**
     * возвращает всех людей
     */
    public List<Person> index()
    {
        return connectionUtils.getJdbcTemplate().query("SELECT * FROM Person", new BeanPropertyRowMapper<>(Person.class));
    }

    public Person show(int id)
    {
        return connectionUtils.getJdbcTemplate().query("SELECT * FROM Person WHERE id=?", new Object[]{id},
                new BeanPropertyRowMapper<>(Person.class)).stream().findAny().orElse(null);
    }

    public void sava(Person person)
    {
        connectionUtils.getJdbcTemplate().update( "INSERT INTO Person VALUES(1, ?, ?, ?)", person.getName(), person.getAge(),
                person.getEmail());
    }

    public void update(int id, Person person)
    {
        connectionUtils.getJdbcTemplate().update("UPDATE Person SET name=?, age=?, email=? WHERE id=?", person.getName(), person.getAge(),
               person.getEmail(), id);
    }

    public void delete(int id)
    {
        connectionUtils.getJdbcTemplate().update("DELETE FROM Person WHERE id=?", id);
    }
}
