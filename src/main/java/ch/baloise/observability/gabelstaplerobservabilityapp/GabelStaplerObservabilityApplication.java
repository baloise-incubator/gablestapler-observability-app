package ch.baloise.observability.gabelstaplerobservabilityapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import reactor.core.publisher.Hooks;

import java.time.Duration;

@SpringBootApplication
@EnableScheduling
public class GabelStaplerObservabilityApplication {
	public static void main(String[] args) {
		Hooks.enableAutomaticContextPropagation();
		SpringApplication.run(GabelStaplerObservabilityApplication.class, args);
	}

}
