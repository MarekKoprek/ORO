package com.example.oro3.service;

import com.example.oro3.model.Person;
import com.example.oro3.model.PersonType;
import com.example.oro3.model.Topic;
import com.example.oro3.repo.TopicRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TopicService {

    private final TopicRepo topicRepo;

    public Topic createTopic(String name, Person organizer){
        if(organizer.getType() != PersonType.ORGANIZER){
            throw new IllegalArgumentException("Person must be organizer");
        }
        return topicRepo.save(new Topic(null, name, organizer));
    }

    public List<Topic> getAllTopics(){
        return topicRepo.findAll();
    }
}
