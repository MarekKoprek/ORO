package com.example.oro1;

import com.example.oro1.Model.*;
import com.example.oro1.Repo.CategoryRepo;
import com.example.oro1.Repo.CustomerRepo;
import com.example.oro1.Repo.PartRepo;
import com.example.oro1.Repo.TransactionRepo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Slf4j
@SpringBootTest
class Oro1ApplicationTests {

	@Autowired
	private PartRepo partRepo;

	@Autowired
	private TransactionRepo transactionRepo;

	@Autowired
	private CustomerRepo customerRepo;

	@Autowired
	private CategoryRepo categoryRepo;

	@Test
	void contextLoads() {
		Customer customer1 = createCustomer("Jan", "Kowalski", "jankowalski@gmail.com");
		Customer customer2 = createCustomer("Kamil", "Nowak", "kamilnowak@gmail.com");

		Category category1 = createCategory("Suspention");
		Category category2 = createCategory("Exhaust");
		Category category3 = createCategory("Wheels");

		Part tire = createPart("Toyo tire", category3, List.of(Model.HONDA, Model.YAMAHA), 249.99);
		Part exhaustManifold = createPart("Exhaust manifold", category2, List.of(Model.KAWASAKI), 199);
		Part damper = createPart("Damper", category1, List.of(Model.KAWASAKI, Model.HONDA), 124.78);

		Transaction transaction1 = createTransaction(tire, customer1);
		Transaction transaction2 = createTransaction(exhaustManifold, customer1);
		Transaction transaction3 = createTransaction(damper, customer2);
		Transaction transaction4 = createTransaction(tire, customer2);
		Transaction transaction5 = createTransaction(tire, customer1);
		Transaction transaction6 = createTransaction(exhaustManifold, customer2);
		Transaction transaction7 = createTransaction(exhaustManifold, customer2);

		log.info("\n------------------------------------------------------------------------------------------------------------------------------------");

		long countTransactions = transactionRepo.count();
		assertThat(countTransactions).isEqualTo(7);

		log.info("Liczba wszystkich tranzakcji: {}", countTransactions);
		transactionRepo.findAll().forEach(tr -> log.info("Klient: {} {} | Część: {}", tr.getCustomer().getFirstName(),
												tr.getCustomer().getLastName(), tr.getPart().getName()));

		log.info("\n------------------------------------------------------------------------------------------------------------------------------------");

		long countTransactionsByTire = transactionRepo.countByPart(tire);
		assertThat(countTransactionsByTire).isEqualTo(3);
		log.info("Liczba zakupionych opon: {}", countTransactionsByTire);

		long countTransactionsByDamper = transactionRepo.countByPart(damper);
		assertThat(countTransactionsByDamper).isEqualTo(1);
		log.info("Liczba zakupionych amortyzatorów: {}", countTransactionsByDamper);

		long countTransactionsByExhaustManifold = transactionRepo.countByPart(exhaustManifold);
		assertThat(countTransactionsByExhaustManifold).isEqualTo(3);
		log.info("Liczba zakupionych kolektorów wydechowych: {}", countTransactionsByExhaustManifold);

		log.info("\n------------------------------------------------------------------------------------------------------------------------------------");

		long countTransactionByCustomer = transactionRepo.countByCustomer(customer1);
		assertThat(countTransactionByCustomer).isEqualTo(3);
		log.info("Liczba tranzakcji klienta 1: {}", countTransactionByCustomer);

		log.info("\n------------------------------------------------------------------------------------------------------------------------------------");

		long countTransactionByEmail = transactionRepo.countByCustomerEmail("kamilnowak@gmail.com");
		assertThat(countTransactionByEmail).isEqualTo(4);
		log.info("Liczba tranzakcji według maila kamilnowak@gmail.com: {}", countTransactionByEmail);

		log.info("\n------------------------------------------------------------------------------------------------------------------------------------");

		List<Part> partsByPrice = partRepo.findAllByPriceBetween(1, 400);
		assertThat(partsByPrice.size()).isEqualTo(3);
		log.info("Lista części w przedziale cenowym: {}-{}", 1, 400);
		partsByPrice.forEach(pt -> log.info("{}: {}", pt.getName(), pt.getPrice()));
		log.info("");

		partsByPrice = partRepo.findAllByPriceBetween(150, 400);
		assertThat(partsByPrice.size()).isEqualTo(2);
		log.info("Lista części w przedziale cenowym: {}-{}", 150, 400);
		partsByPrice.forEach(pt -> log.info("{}: {}", pt.getName(), pt.getPrice()));
		log.info("");

		partsByPrice = partRepo.findAllByPriceBetween(150, 230);
		assertThat(partsByPrice.size()).isEqualTo(1);
		log.info("Lista części w przedziale cenowym: {}-{}", 150, 230);
		partsByPrice.forEach(pt -> log.info("{}: {}", pt.getName(), pt.getPrice()));
		log.info("");
	}

	private Customer createCustomer(String firstName, String lastName, String email) {
		return customerRepo.save(new Customer().withFirstName(firstName).withLastName(lastName).withEmail(email));
	}

	private Category createCategory(String name){
		return categoryRepo.save(new Category(null, name));
	}

	private Part createPart(String name, Category category, List<Model> models, double price){
		return partRepo.save(new Part(null, name, category, models, price));
	}

	private Transaction createTransaction(Part part, Customer customer){
		return transactionRepo.save(new Transaction(null, part, customer));
	}
}
