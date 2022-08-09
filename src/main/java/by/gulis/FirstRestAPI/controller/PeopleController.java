package by.gulis.FirstRestAPI.controller;

import by.gulis.FirstRestAPI.DTO.PersonDTO;
import by.gulis.FirstRestAPI.models.Person;
import by.gulis.FirstRestAPI.services.PeopleService;
import by.gulis.FirstRestAPI.util.PersonErrorResponse;
import by.gulis.FirstRestAPI.util.PersonNotCreatedException;
import by.gulis.FirstRestAPI.util.PersonNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/people")
public class PeopleController {

    private PeopleService peopleService;

    private ModelMapper modelMapper;

    @Autowired
    public PeopleController(PeopleService peopleService,ModelMapper modelMapper) {
        this.peopleService = peopleService;
        this.modelMapper = modelMapper;
    }

    @GetMapping()
    public List<PersonDTO> getPeople(){
        return peopleService.findAll().stream().map(this::convertToPersonDTO).collect(Collectors.toList());
    }
    @GetMapping("{id}")
    public PersonDTO getPerson(@PathVariable("id") int id){
        return convertToPersonDTO(peopleService.findOne(id));
    }

    @ExceptionHandler
    private ResponseEntity<PersonErrorResponse> handleException(PersonNotFoundException e){
        PersonErrorResponse p = new PersonErrorResponse("Person was n`t found with this Id!", System.currentTimeMillis());
        return new ResponseEntity<>(p, HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid PersonDTO personDTO, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            StringBuilder str = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError e : errors
            ) {
                str.append(e.getField())
                        .append(" - ")
                        .append(e.getDefaultMessage())
                        .append(";");
            }
            throw new PersonNotCreatedException(str.toString());
        }
        peopleService.save(convertToPerson(personDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<PersonErrorResponse> handleException(PersonNotCreatedException e){
        PersonErrorResponse p = new PersonErrorResponse(e.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(p, HttpStatus.BAD_REQUEST);
    }

    private Person convertToPerson(PersonDTO personDTO){
        Person person = modelMapper.map(personDTO, Person.class);
        return person;
    }

    private PersonDTO convertToPersonDTO(Person person){
        PersonDTO personDTO = modelMapper.map(person, PersonDTO.class);
        return personDTO;
    }

}
