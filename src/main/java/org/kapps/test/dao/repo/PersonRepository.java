package org.kapps.test.dao.repo;

import org.kapps.test.dao.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Antoine Kapps
 */
@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {
}
