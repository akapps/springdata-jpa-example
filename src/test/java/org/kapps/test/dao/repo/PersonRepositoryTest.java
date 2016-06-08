package org.kapps.test.dao.repo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.kapps.test.dao.Person;
import org.kapps.test.spring.FullstackConfig;
import org.kapps.test.spring.TestDatasourceConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Antoine Kapps
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { FullstackConfig.class, TestDatasourceConfig.class})
public class PersonRepositoryTest {

    @Autowired
    private PersonRepository repository;

    @Test
    @Sql(statements = {
            "INSERT INTO PERSONS (id, firstname, lastname) VALUES (1, 'John', 'Doe')",
            "INSERT INTO ANIMALS (id, name, age, personId) VALUES (1, 'Johnny the Cat', 3, 1)",
            "INSERT INTO ANIMALS (id, name, age, personId) VALUES (2, 'Bob the Dog', 8, 1)"
    })
    @Sql(statements = {
            "DELETE FROM ANIMALS",
            "DELETE FROM PERSONS"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Transactional
    public void countFriends() {
        final Person person = repository.getOne(1);

        assertThat(person.getFriends()).as("John Doe's linked Pets").hasSize(2);
    }

}