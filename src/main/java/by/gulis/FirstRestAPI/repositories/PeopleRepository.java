package by.gulis.FirstRestAPI.repositories;

import by.gulis.FirstRestAPI.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PeopleRepository extends JpaRepository<Person, Integer> {
    List<Person> findByFullName(String fullName);
}