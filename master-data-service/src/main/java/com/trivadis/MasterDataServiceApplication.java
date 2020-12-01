package com.trivadis;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@SpringBootApplication
@EnableJms
public class MasterDataServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MasterDataServiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner run(BookService bookService) {
		return (args) -> {
			bookService.createBook(new Book("Das Café am Rande der Welt: eine Erzählung über den Sinn des Lebens",
					new Author("John Strelecky")));

			bookService.createBook(new Book("Alle Welt: Das Landkartenbuch. Erweiterte Neuausgabe",
					new Author("Aleksandra Mizielinska")));
		};
	}
}
