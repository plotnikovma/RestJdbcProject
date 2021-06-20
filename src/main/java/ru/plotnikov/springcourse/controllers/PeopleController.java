package ru.plotnikov.springcourse.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

import ru.plotnikov.springcourse.dao.DAO;
import ru.plotnikov.springcourse.dao.PersonDAOJdbcDriver;
import ru.plotnikov.springcourse.entity.Person;

/**
 * Контроллер обработки запросов
 *
 * @author mplotnikov
 * @since 15.06.2021
 */
@Controller
@RequestMapping("/people")
public class PeopleController
{
    @Qualifier("personDAOJdbcTemplate")
    //@Qualifier("personDAOJdbcDriver")
    private final DAO personDAO;

    @Autowired
    public PeopleController(PersonDAOJdbcDriver personDAO)
    {
        this.personDAO = personDAO;
    }

    /**
     * Получение всех людей и передача их представлению
     */
    @GetMapping()
    public String index(Model model)
    {
        model.addAttribute("people", personDAO.index());

        return "people/index";
    }

    /**
     * Получение человека по id и вернуть его представлению
     */
    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model)
    {
        model.addAttribute("person", personDAO.show(id));

        return "people/show";
    }

    @GetMapping("/new")
    public String newPerson(Model model)
    {
        model.addAttribute("person", new Person());

        return "people/new";
    }

    @PostMapping
    public String create(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult)
    {
        /*
        Если при валидации атрибутов person возникли ошибки, они будут помещены в объект bindingResult,
        и в таком случае вернем представление people/new, в котором отображены ошибки
         */
        if (bindingResult.hasErrors())
        {
            return "people/new";
        }
        personDAO.sava(person);

        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id)
    {
        model.addAttribute("person",personDAO.show(id));

        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult,
            @PathVariable("id") int id)
    {
        if (bindingResult.hasErrors())
        {
            return "people/edit";
        }
        personDAO.update(id, person);

        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id)
    {
        personDAO.delete(id);

        return "redirect:/people";
    }
}
