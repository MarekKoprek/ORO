package com.example.oro2.repo;

import com.example.oro2.model.Performance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PerformanceRepo extends JpaRepository<Performance, Long> {
    Page<Performance> findByRoomId(Long roomId, Pageable pageable);

    Page<Performance> findByArtId(Long artId, Pageable pageable);

    Page<Performance> findByArtName(String artName, Pageable pageable);

    @Query("SELECT COUNT(DISTINCT p.room.number) FROM Performance p WHERE p.art.id = :artId")
    int countRoomsByArtId(@Param("artId") Long artId);
}
