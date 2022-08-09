package by.gulis.FirstRestAPI.services;

import by.gulis.FirstRestAPI.models.Person;
import by.gulis.FirstRestAPI.repositories.PeopleRepository;
import by.gulis.FirstRestAPI.util.PersonNotFoundException;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PeopleService {

    private final PeopleRepository peopleRepository;
    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public List<Person> findAll(){
        return peopleRepository.findAll();
    }

    public Person findOne(int id){
        Optional<Person> person = peopleRepository.findById(id);
        return person.orElseThrow(PersonNotFoundException::new);
    }

    @Transactional
    public void save(Person person){
        enrichMethod(person);
        peopleRepository.save(person);
    }

    private void enrichMethod(Person person){
        person.setCreatedAt(LocalDateTime.now());
        person.setUpdatedAt(LocalDateTime.now());
        person.setCreatedWho("admin");
    }
}