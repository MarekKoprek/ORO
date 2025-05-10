package com.example.oro3.service;

import com.example.oro3.model.Person;
import com.example.oro3.model.PersonType;
import com.example.oro3.repo.PersonRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepo personRepo;

    public Person createPerson(String firstName, String lastName, PersonType personType, String country){
        return personRepo.save(new Person(null, firstName, lastName, personType, false, country));
    }

    public List<Person> getAllAttendees(){
        return personRepo.findAllAttendees();
    }

    public Map<PersonType, List<Person>> getAllPeopleSortedByType(){
        return personRepo.findAll()
                .stream()
                .collect(Collectors.groupingBy(Person::getType));
    }

    public Map<String, List<Person>> getAllPeopleSortedByCountry(){
        return personRepo.findAll()
                .stream()
                .collect(Collectors.groupingBy(Person::getCountry));
    }
}
