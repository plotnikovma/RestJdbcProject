package ru.plotnikov.springcourse.dao;

import java.util.List;

import ru.plotnikov.springcourse.entity.Person;

/**
 * Интерфейс, который реализуют DAO классы
 *
 * @author mplotnikov
 * @since 20.06.2021
 */
public interface DAO
{
    /**
     * @return Список всехзарегистрированных людей
     */
    List<Person> index();

    /**
     * возвращает человека по индексу
     */
    Person show(int id);

    /**
     * @param person человек, которохо необходимо сохранить в БД
     */
    void sava(Person person);

    /**
     * изменяем параметры человека
     * @param id человека
     * @param person новая сущность человека с изменениями
     */
    void update(int id, Person person);

    void delete(int id);
}