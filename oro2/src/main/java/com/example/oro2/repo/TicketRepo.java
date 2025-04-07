package com.example.oro2.repo;

import com.example.oro2.model.Client;
import com.example.oro2.model.Performance;
import com.example.oro2.model.ReservationType;
import com.example.oro2.model.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface TicketRepo extends JpaRepository<Ticket, Long> {
    @Query("SELECT t.client FROM Ticket t WHERE t.performance.id = :performanceId")
    Page<Client> findClientsByPerformanceId(@Param("performanceId") Long performanceId, Pageable pageable);

    @Query("SELECT t.performance FROM Ticket t WHERE t.client.id = :clientId")
    Page<Performance> findPerformancesByClientId(@Param("clientId") Long clientId, Pageable pageable);

    @Query("SELECT t.performance FROM Ticket t WHERE t.client.login = :clientLogin")
    Page<Performance> findPerformancesByClientLogin(@Param("clientLogin") String clientLogin, Pageable pageable);

    @Query("SELECT COUNT(t) FROM Ticket t WHERE t.performance.date BETWEEN :startDate AND :endDate AND t.client.id = :clientId AND t.type = :accepted")
    int countTicketsByClientDateBetween(@Param("startDate") LocalDateTime startDate,
                                        @Param("endDate") LocalDateTime endDate,
                                        @Param("clientId") Long clientId,
                                        @Param("accepted") ReservationType accepted);

    default int countTicketsByClientDateBetween(LocalDateTime startDate, LocalDateTime endDate, Long clientId) {
        return countTicketsByClientDateBetween(startDate, endDate, clientId, ReservationType.ACCEPTED);
    }

    @Query("SELECT COUNT(t) FROM Ticket t WHERE t.performance.room.number = :roomNumber " +
            "AND t.performance.date = :date " +
            "AND (t.type = :pending OR t.type = :accepted)")
    int countOccupiedSeatsByRoomNumberDate(@Param("roomNumber") int roomNumber,
                                           @Param("date") LocalDateTime date,
                                           @Param("pending") ReservationType pending,
                                           @Param("accepted") ReservationType accepted);

    default int countOccupiedSeatsByRoomNumberDate(int roomNumber, LocalDateTime date) {
        return countOccupiedSeatsByRoomNumberDate(roomNumber, date, ReservationType.PENDING, ReservationType.ACCEPTED);
    }
}
