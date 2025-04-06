package org.afa.borrowingservice;

import org.afa.borrowingservice.entities.Borrow;
import org.afa.borrowingservice.repository.BorrowRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@SpringBootApplication
public class BorrowingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BorrowingServiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(BorrowRepository repository) {
		return args -> {
			repository.save(Borrow
					.builder()
							.borrower("studentA").bookId(1L).borrowDate(LocalDateTime.now()).returnDate(LocalDateTime.now().plusDays(7))
					.build());
			repository.save(Borrow
					.builder()
					.borrower("ContractorA").bookId(2L).borrowDate(LocalDateTime.now()).returnDate(LocalDateTime.now().plusDays(14))
					.build());
			repository.save(Borrow
					.builder()
					.borrower("SalaryB").bookId(3L).borrowDate(LocalDateTime.now()).returnDate(LocalDateTime.now().plusDays(5))
					.build());
			System.out.println("=================Display the saved objects===========================");
			repository.findAll().forEach(System.out::println);
			System.out.println("=====================================================================");
		};
	}
}
