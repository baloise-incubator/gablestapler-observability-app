package ch.baloise.observability.gabelstaplerobservabilityapp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin
public class ObservabilityController {

    private final WebClient webClient;

    @Value("${spring.application.name}")
    String applicationName;

    @Value("${use-cases.endpoint-one}")
    String endpointOne;

    @Value("${use-cases.endpoint-two}")
    String endpointTwo;

    @GetMapping("/endpoint")
    Mono<String> endpoint() throws Exception {
        log.info("Endpoint called");

        if (endpointOne != null && !endpointOne.isEmpty()) {
            webClient.get()
                    .uri(endpointOne + "/endpoint")
                    .retrieve()
                    .bodyToMono(String.class)
                    .subscribe(s -> {
                        log.info("Received response from endpointOne: %s".formatted(s));
                    });
        }
        if (endpointTwo != null && !endpointTwo.isEmpty()) {
            webClient.get()
                    .uri(endpointTwo + "/endpoint")
                    .retrieve()
                    .bodyToMono(String.class)
                    .subscribe(s -> {
                        log.info("Received response from endpointTwo: %s".formatted(s));
                    });
        }
        this.sleep();
        return Mono.just("Response from %s".formatted(applicationName));
    }

    private void sleep() throws InterruptedException {
        int upper = 7000;
        int lower = 0;
        int r = (int) (Math.random() * (upper - lower)) + lower;
        log.info("Sleep for %s milli".formatted(r));
        Thread.sleep(15000);
    }
}
