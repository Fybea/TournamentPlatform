package com.platform.TournamentPlatform;

import com.platform.TournamentPlatform.exception.ExceptionHandlerController;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableScheduling
public class TournamentPlatformApplication extends ExceptionHandlerController {

	public static void main(String[] args) {
		SpringApplication.run(TournamentPlatformApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
}
