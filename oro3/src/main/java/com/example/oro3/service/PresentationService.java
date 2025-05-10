package com.example.oro3.service;

import com.example.oro3.model.*;
import com.example.oro3.repo.PresentationRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PresentationService {

    private final PresentationRepo presentationRepo;

    public Presentation createPresentation(LocalDateTime startDate, LocalDateTime endDate, Room room, Topic topic) {
        if(presentationRepo.existsByRoomAndTimeOverlap(room, startDate, endDate)){
            throw new IllegalArgumentException("Time overlap");
        }
        if(presentationRepo.existsByTopic(topic)){
            throw new IllegalArgumentException("Topic already exists");
        }
        return presentationRepo.save(new Presentation(null, startDate, endDate, room, topic, new ArrayList<>()));
    }

    public void addAttendance(Presentation presentation, Person person){
        if(person.getType() == PersonType.ORGANIZER){
            throw new IllegalArgumentException("Organizers cannot attend");
        }
        person.setActive(true);
        presentation.getAttendees().add(person);
        presentationRepo.save(presentation);
    }

    public Person getTopOrganizer(){
        Page<Person> page = presentationRepo.findTopOrganizer(PageRequest.of(0, 1));
        return page.hasContent() ? page.getContent().getFirst() : null;
    }

    public Map<Integer, Long> countPresentationsForRooms() {
        List<Object[]> results = presentationRepo.countPresentationsPerRoom();
        return results.stream()
                .collect(Collectors.toMap(
                        row -> (Integer) row[0],
                        row -> (Long) row[1]
                ));
    }
}
