package com.example.oro3;

import com.example.oro3.model.*;
import com.example.oro3.service.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Slf4j
@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class Oro3ApplicationTests {

	private final PresentationService presentationService;
	private final HotelService hotelService;
	private final TopicService topicService;
	private final PersonService personService;
	private final RoomService roomService;

	public Oro3ApplicationTests(PresentationService presentationService,
								HotelService hotelService,
								TopicService topicService,
								PersonService personService,
								RoomService roomService) {
		this.presentationService = presentationService;
		this.hotelService = hotelService;
		this.topicService = topicService;
		this.personService = personService;
		this.roomService = roomService;
	}

	@Test
	@Transactional
	void contextLoads() {
		Room room1 = roomService.createRoom(1);
		Room room2 = roomService.createRoom(2);
		Room room3 = roomService.createRoom(3);

		Hotel hotel = hotelService.createHotel(List.of(room1, room2, room3));

		Person janKowalski = personService.createPerson("Jan", "Kowalski", PersonType.DOCTOR, "Polska");
		Person janNowak = personService.createPerson("Jan", "Nowak", PersonType.DOCTOR, "Polska");
		Person marekKoprek = personService.createPerson("Marek", "Koprek", PersonType.STUDENT, "Polska");
		Person horheMartines = personService.createPerson("Horhe", "Martines", PersonType.ORGANIZER, "Meksyk");
		Person siergiejDubov = personService.createPerson("Siergiej", "Dubov", PersonType.ORGANIZER, "Rosja");

		Topic topic1 = topicService.createTopic("Topic 1", horheMartines);
		Topic topic2 = topicService.createTopic("Topic 2", siergiejDubov);
		Topic topic3 = topicService.createTopic("Topic 3", horheMartines);
		Topic topic4 = topicService.createTopic("Topic 4", horheMartines);
		Topic topic5 = topicService.createTopic("Topic 5", siergiejDubov);
		Topic topic6 = topicService.createTopic("Topic 6", horheMartines);
		try {
			Topic topic7 = topicService.createTopic("Topic 7", janKowalski);
		} catch (IllegalArgumentException e){
			log.info(e.getMessage());
		}

		LocalDateTime date1 = LocalDateTime.of(2021, 1, 1, 10, 0);
		LocalDateTime date2 = LocalDateTime.of(2021, 1, 1, 11, 0);
		LocalDateTime date3 = LocalDateTime.of(2021, 1, 1, 12, 0);
		LocalDateTime date4 = LocalDateTime.of(2021, 1, 1, 13, 0);
		LocalDateTime date5 = LocalDateTime.of(2021, 1, 1, 14, 0);

		Presentation presentation1 = presentationService.createPresentation(date1, date1.plusHours(1), room1, topic1);
		Presentation presentation2 = presentationService.createPresentation(date2, date2.plusHours(1), room2, topic2);
		Presentation presentation3 = presentationService.createPresentation(date3, date3.plusHours(1), room3, topic3);
		Presentation presentation4 = presentationService.createPresentation(date4, date4.plusHours(1), room1, topic4);
		Presentation presentation5 = presentationService.createPresentation(date5, date5.plusHours(1), room2, topic5);
		try{
			Presentation presentation6 = presentationService.createPresentation(date1, date1.plusHours(1), room3, topic1);
		} catch (IllegalArgumentException e){
			log.info(e.getMessage());
		}
		try{
			Presentation presentation7 = presentationService.createPresentation(date1.plusMinutes(30), date2.plusMinutes(30), room1, topic6);
		} catch (IllegalArgumentException e){
			log.info(e.getMessage());
		}

		presentationService.addAttendance(presentation1, janNowak);
		presentationService.addAttendance(presentation2, marekKoprek);
		presentationService.addAttendance(presentation3, janKowalski);

		log.info("-----------------------------------------------------------------------------------------------------------------------\n");

		log.info("Lista wszystkich uczestników sympozjum:");
		List<Person> allAttendees = personService.getAllAttendees();
		allAttendees.forEach(person -> log.info("\t" + person.getFirstName() + " " + person.getLastName()));

		log.info("\n-----------------------------------------------------------------------------------------------------------------------\n");

		log.info("Lista wszystkich ludzi z podziałem na role:");
		Map<PersonType, List<Person>> peopleSortedByType = personService.getAllPeopleSortedByType();
		peopleSortedByType.forEach((key, value) -> {
			log.info("\t" + key.name() + ":");
			value.forEach(person -> log.info("\t\t" + person.getFirstName() + " " + person.getLastName()));
		});

		log.info("\n-----------------------------------------------------------------------------------------------------------------------\n");

		log.info("Lista wszystkich ludzi z podziałem na kraj:");
		Map<String, List<Person>> peopleSortedByCountry = personService.getAllPeopleSortedByCountry();
		peopleSortedByCountry.forEach((key, value) -> {
			log.info("\t" + key + ":");
			value.forEach(person -> log.info("\t\t" + person.getFirstName() + " " + person.getLastName()));
		});

		log.info("\n-----------------------------------------------------------------------------------------------------------------------\n");

		log.info("Lista tematów prezentacji:");
		List<Topic> allTopics = topicService.getAllTopics();
		allTopics.forEach(topic -> log.info("\t" + topic.getName()));

		log.info("\n-----------------------------------------------------------------------------------------------------------------------\n");

		Person topOrganizer = presentationService.getTopOrganizer();
		log.info("Organizator z największą liczbą prezentacji:");
		log.info("\t" + topOrganizer.getFirstName() + " " + topOrganizer.getLastName());

		log.info("\n-----------------------------------------------------------------------------------------------------------------------\n");

		Map<Integer, Long> countPresentationsForRooms = presentationService.countPresentationsForRooms();
		log.info("Liczba prezentacji dla każdej sali:");
		countPresentationsForRooms.forEach((key, value) -> log.info("\t" + key + ": " + value));

		log.info("\n-----------------------------------------------------------------------------------------------------------------------\n");
	}

}
