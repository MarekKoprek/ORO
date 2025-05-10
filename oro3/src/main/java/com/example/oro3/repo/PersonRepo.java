package com.example.oro3.repo;

import com.example.oro3.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PersonRepo extends JpaRepository<Person, Long> {

    @Query("SELECT p FROM Person p WHERE (p.type = 'STUDENT' OR p.type = 'DOCTOR') AND p.active = true")
    List<Person> findAllAttendees();

}
