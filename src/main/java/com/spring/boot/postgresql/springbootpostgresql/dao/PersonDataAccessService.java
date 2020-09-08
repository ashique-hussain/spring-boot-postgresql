package com.spring.boot.postgresql.springbootpostgresql.dao;


import com.spring.boot.postgresql.springbootpostgresql.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("postgres")
public class PersonDataAccessService implements PersonDao {

    private final JdbcTemplate jdbcTemplate;
    //private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public PersonDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        //this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
    }

    @Override
    public int insertPerson(UUID id, Person person) {
        final String sql = "INSERT INTO person (id, name) VALUES(?, ?)";
        //KeyHolder keyHolder = new GeneratedKeyHolder();
        /*SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("id", id)
                .addValue("name", person.getName());*/
        jdbcTemplate.update(sql, id, person.getName());
        return 1;
    }

    @Override
    public List<Person> selectAllPeople() {
        final String sql = "SELECT * FROM person";
        List<Person> result = jdbcTemplate.query(sql, (resultSet, i) -> {
            UUID id = UUID.fromString(resultSet.getString("id"));
            String name = resultSet.getString("name");
            return new Person(id, name);
        });
        System.out.println(result);
        return result;
    }

    @Override
    public Optional<Person> selectPersonById(UUID id) {
        final String sql = "SELECT id, name FROM person WHERE id = ?";
        Person person = jdbcTemplate.queryForObject(
                sql,
                new Object[]{id},
                (resultSet, i) -> {
         UUID personId = UUID.fromString(resultSet.getString("id"));
         String name = resultSet.getString("name");
         return new Person(id, name);
        });
        return Optional.ofNullable(person);
    }

    @Override
    public int deletePersonById(UUID id) {
        final String sql = "DELETE FROM person WHERE id= ?";
        jdbcTemplate.update(sql, id);
        return 0;
    }

    @Override
    public int updatePersonById(UUID id, Person person) {
        final  String sql = "UPDATE person SET name=? WHERE id=?";
        jdbcTemplate.update(sql, person.getName(), id);
        return 1;
    }
}
