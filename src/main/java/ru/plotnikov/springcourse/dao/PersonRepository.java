package ru.plotnikov.springcourse.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ru.plotnikov.springcourse.entity.Person;

/**
 *
 *
 * @author mplotnikov
 * @since 26.06.2021
 */
@Repository
public interface PersonRepository extends CrudRepository<Person, Integer>
{
}