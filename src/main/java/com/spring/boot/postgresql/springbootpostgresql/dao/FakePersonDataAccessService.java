package com.spring.boot.postgresql.springbootpostgresql.dao;

import com.spring.boot.postgresql.springbootpostgresql.model.Person;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("fakeDao")
public class FakePersonDataAccessService implements PersonDao {

    private static List<Person> DB = new ArrayList<>();

    @Override
    public int insertPerson(UUID id, Person person) {
        DB.add(new Person(id, person.getName()));
        return 1;
    }

    @Override
    public List<Person> selectAllPeople() {
        return DB;
    }

    @Override
    public Optional<Person> selectPersonById(UUID id) {
        return DB.stream()
                .filter(person -> person.getId().equals(id))
                .findFirst();
    }

    @Override
    public int deletePersonById(UUID id) {
        Optional<Person> personMayBe = selectPersonById(id);
        if (!personMayBe.isPresent()) {
            return 0;
        }
        DB.remove(personMayBe.get());
        return 1;
    }

    @Override
    public int updatePersonById(UUID id, Person updatePerson) {
        return selectPersonById(id)
                .map(person1 -> {
                    int indexOfPersonToUpdate = DB.indexOf(person1);
                    if(indexOfPersonToUpdate >= 0){
                        DB.set(indexOfPersonToUpdate, new Person(id, updatePerson.getName()));
                        return 1;
                    }
                    return 0;
                })
                .orElse(0);
    }

}
