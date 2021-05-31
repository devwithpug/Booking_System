package kgu.sw.team1.booksys;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BookSysApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookSysApplication.class, args);
	}

}
