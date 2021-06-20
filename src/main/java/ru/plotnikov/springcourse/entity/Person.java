package ru.plotnikov.springcourse.entity;

import java.util.Objects;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 *
 *
 * @author mplotnikov
 * @since 20.06.2021
 */
public class Person
{
    private int id;

    @NotEmpty(message = "Name should not be Empty!")
    @Size(min = 5, max = 30, message = "Size of name must be between 5 and 30 characters")
    private String name;

    @Min(value = 0L, message = "Age should be greater then 0")
    private int age;

    @NotEmpty(message = "Email should not be Empty!")
    @Email(message = "Email should be valid")
    private String email;

    public Person() {
    }

    public Person(int id, String name, int age, String email) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return this.age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (!(o instanceof Person))
            return false;
        Person person = (Person)o;
        return getId() == person.getId() && getAge() == person.getAge() && Objects.equals(getName(),
                person.getName()) && Objects.equals(getEmail(), person.getEmail());
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(getId(), getName(), getAge(), getEmail());
    }
}