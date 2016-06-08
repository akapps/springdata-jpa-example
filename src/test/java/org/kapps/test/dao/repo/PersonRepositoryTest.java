package org.kapps.test.dao.repo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.kapps.test.dao.Person;
import org.kapps.test.spring.FullstackConfig;
import org.kapps.test.spring.TestDatasourceConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Antoine Kapps
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { FullstackConfig.class, TestDatasourceConfig.class})
public class PersonRepositoryTest {

    @Autowired
    private PersonRepository repository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    @Sql(statements = {
            "INSERT INTO PERSONS (id, firstname, lastname) VALUES (1, 'John', 'Doe')",
            "INSERT INTO ANIMALS (id, name, age, personId) VALUES (1, 'Johnny the Cat', 3, 1)",
            "INSERT INTO ANIMALS (id, name, age, personId) VALUES (2, 'Bob the Dog', 8, 1)"
    })
    @Transactional
    public void countFriends() {
        final Person person = repository.getOne(1);

        assertThat(person.getFriends()).as("John Doe's linked Pets").hasSize(2);
    }


    @Test
    @Sql(statements = {
            "INSERT INTO PERSONS (id, firstname, lastname) VALUES (2, 'Bob', 'Lennon')"
    })
    @Transactional
    public void addNewPerson() {
        Person another = new Person();
        another.setId(3);
        another.setFirstname("Toto");
        another.setLastname("Brindacier");
        another.setBirthdate(LocalDate.of(1983, 5, 29));

        repository.saveAndFlush(another);


        assertThat(JdbcTestUtils.countRowsInTable(jdbcTemplate, "PERSONS"))
                .isEqualTo(2);
        assertThat(JdbcTestUtils.countRowsInTable(jdbcTemplate, "ANIMALS"))
                .isEqualTo(0);

        final Map<String, Object> rs = jdbcTemplate.queryForMap("SELECT * FROM PERSONS WHERE ID = 3");
        assertThat(rs.get("firstname")).isEqualTo("Toto");
        assertThat(rs.get("lastname")).isEqualTo("Brindacier");
        assertThat(rs.get("birthdate")).isEqualTo(Date.valueOf("1983-05-29"));
    }

}