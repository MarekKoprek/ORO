package com.example.oro3.repo;

import com.example.oro3.model.Person;
import com.example.oro3.model.Presentation;
import com.example.oro3.model.Room;
import com.example.oro3.model.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface PresentationRepo extends JpaRepository<Presentation, Long> {

    @Query("""
        SELECT COUNT(p) > 0 FROM Presentation p
        WHERE p.room = :room
          AND :startDate < p.endDate
          AND :endDate > p.startDate
    """)
    boolean existsByRoomAndTimeOverlap(@Param("room") Room room,
                                       @Param("startDate") LocalDateTime startDate,
                                       @Param("endDate") LocalDateTime endDate);

    @Query("SELECT t.organizer FROM Presentation p JOIN p.topic t GROUP BY t.organizer ORDER BY COUNT(p) DESC")
    Page<Person> findTopOrganizer(Pageable pageable);

    @Query("SELECT r.number, COUNT(p.id) FROM Presentation p JOIN p.room r GROUP BY r.number")
    List<Object[]> countPresentationsPerRoom();

    @Query("SELECT COUNT(p) > 0 FROM Presentation p WHERE p.topic = :topic")
    boolean existsByTopic(@Param("topic") Topic topic);
}
