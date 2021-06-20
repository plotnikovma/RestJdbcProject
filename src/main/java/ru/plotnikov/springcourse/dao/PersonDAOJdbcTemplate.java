package ru.plotnikov.springcourse.dao;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Component;

import ru.plotnikov.springcourse.entity.Person;
import ru.plotnikov.springcourse.utils.JdbcUtil;

/**
 *
 *
 * @author mplotnikov
 * @since 17.06.2021
 */
@Component
public class PersonDAOJdbcTemplate implements DAO
{
    private final JdbcUtil jdbcUtil;

    public PersonDAOJdbcTemplate(JdbcUtil jdbcUtil)
    {
        this.jdbcUtil = jdbcUtil;
    }

    /**
     * возвращает всех людей
     */
    public List<Person> index()
    {
        return jdbcUtil.getJdbcTemplate().query("SELECT * FROM Person", new BeanPropertyRowMapper<>(Person.class));
    }

    public Person show(int id)
    {
        return jdbcUtil.getJdbcTemplate().query("SELECT * FROM Person WHERE id=?", new Object[]{id},
                new BeanPropertyRowMapper<>(Person.class)).stream().findAny().orElse(null);
    }

    public void sava(Person person)
    {
        jdbcUtil.getJdbcTemplate().update( "INSERT INTO Person VALUES(1, ?, ?, ?)", person.getName(), person.getAge(),
                person.getEmail());
    }

    public void update(int id, Person person)
    {
        jdbcUtil.getJdbcTemplate().update("UPDATE Person SET name=?, age=?, email=? WHERE id=?", person.getName(), person.getAge(),
               person.getEmail(), id);
    }

    public void delete(int id)
    {
        jdbcUtil.getJdbcTemplate().update("DELETE FROM Person WHERE id=?", id);
    }
}
