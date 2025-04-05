package com.example.oro2;

import com.example.oro2.model.*;
import com.example.oro2.repo.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@Slf4j
@SpringBootTest
class Oro2ApplicationTests {

	@Autowired
	private TicketRepo ticketRepo;

	@Autowired
	private PerformanceRepo performanceRepo;

	@Autowired
	private RoomRepo roomRepo;

	@Autowired
	private ArtRepo artRepo;

	@Autowired
	private ClientRepo clientRepo;

	@Test
	void contextLoads() {
		Room room1 = createRoom(1, 14);
		Room room2 = createRoom(2, 15);
		Room room3 = createRoom(3, 20);

		Client client1 = createClient("Jan", "Nowak", "jannowak@gmail.com");
		Client client2 = createClient("Andrzej", "Kowalski", "andrzejkowalski@wp.pl");

		Art art1 = createArt("Królewna Śnieżka");
		Art art2 = createArt("Romeo i Julia");
		Art art3 = createArt("Piękna i bestia");

		LocalDateTime date1 = LocalDateTime.of(2025, 04, 25, 20, 30);
		LocalDateTime date2 = LocalDateTime.of(2025, 04, 26, 14, 0);
		LocalDateTime date3 = LocalDateTime.of(2025, 05, 26, 16, 45);

		Performance performance1 = createPerformance(room1, art1, date1);
		Performance performance2 = createPerformance(room2, art2, date1);
		Performance performance3 = createPerformance(room3, art3, date1);
		Performance performance4 = createPerformance(room1, art1, date2);
		Performance performance5 = createPerformance(room2, art2, date2);
		Performance performance6 = createPerformance(room3, art3, date2);
		Performance performance7 = createPerformance(room1, art1, date3);
		Performance performance8 = createPerformance(room2, art2, date3);
		Performance performance9 = createPerformance(room3, art3, date3);

		try{
			Performance performanceTest = createPerformance(room1, art2, date1);
		} catch(ConstraintViolationException e){
			log.info("Sala zajęta o tej godzinie");
		}

		Ticket ticket1 = createTicket(LocalDateTime.now().plusMinutes(10), 10, performance1, client1);
		Ticket ticket2 = createTicket(LocalDateTime.now().plusMinutes(10), 12, performance1, client2);
		Ticket ticket3 = createTicket(LocalDateTime.now().plusMinutes(10), 8, performance3, client1);
		Ticket ticket4 = createTicket(LocalDateTime.now().plusMinutes(10), 1, performance2, client1);
		Ticket ticket5 = createTicket(LocalDateTime.now().plusMinutes(10), 2, performance4, client2);
		Ticket ticket6 = createTicket(LocalDateTime.now().plusMinutes(10), 3, performance6, client2);
		Ticket ticket7 = createTicket(LocalDateTime.now().plusMinutes(10), 4, performance5, client1);
		Ticket ticket8 = createTicket(LocalDateTime.now().plusMinutes(10), 5, performance7, client2);
		Ticket ticket9 = createTicket(LocalDateTime.now().plusMinutes(10), 6, performance8, client1);
		Ticket ticket10 = createTicket(LocalDateTime.now().plusMinutes(10), 7, performance9, client1);

		log.info("---------------------------------------------------------------------------------------------------------");


		log.info("---------------------------------------------------------------------------------------------------------");


		log.info("---------------------------------------------------------------------------------------------------------");


		log.info("---------------------------------------------------------------------------------------------------------");


		log.info("---------------------------------------------------------------------------------------------------------");
	}

	private Room createRoom(int number, int seats){
		return roomRepo.save(new Room(null, number, seats));
	}

	private Art createArt(String name){
		return artRepo.save(new Art(null, name));
	}

	private Client createClient(String firstName, String lastName, String email){
		return clientRepo.save(new Client(null, firstName, lastName, email, null, null));
	}

	private Performance createPerformance(Room room, Art art, LocalDateTime date){
		return performanceRepo.save(new Performance(null, room, art, date));
	}

	private Ticket createTicket(LocalDateTime expireDate, int seat, Performance performance, Client client){
		return ticketRepo.save(new Ticket(null, expireDate, ReservationType.PENDING, seat, client, performance));
	}
}
