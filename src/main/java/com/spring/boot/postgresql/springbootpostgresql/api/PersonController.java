package com.spring.boot.postgresql.springbootpostgresql.api;

import com.spring.boot.postgresql.springbootpostgresql.model.Person;
import com.spring.boot.postgresql.springbootpostgresql.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@RequestMapping("api/v1/person")
@RestController
public class PersonController {

    private  final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping
    public void addPerson(@RequestBody Person person){
        personService.addPerson(person);
    }

    @GetMapping
    public List<Person> getAllPeople(){
        return personService.getAllPeople();
    }

    @GetMapping(path = "{id}")
    public Person getPersonById(@PathVariable("id") UUID id) {
        return personService.getPersonById(id)
                .orElse(null);
    }

    @DeleteMapping(path = "{id}")
    public int deletePerson(@PathVariable("id") UUID id) {
        return personService.deletePerson(id);
    }

    @PutMapping(path = "{id}")
    public int updatePerson(@PathVariable("id") UUID id,@Validated @NotNull @RequestBody Person personToUpdate) {
        return personService.updatePerson(id, personToUpdate);
    }

}
