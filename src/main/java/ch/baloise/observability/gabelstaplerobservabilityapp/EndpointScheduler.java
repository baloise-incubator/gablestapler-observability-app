package ch.baloise.observability.gabelstaplerobservabilityapp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@Slf4j
@RequiredArgsConstructor
public class EndpointScheduler {

    private final WebClient webClient;

    @Value("${use-cases.scheduler-endpoint}")
    String schedulerEndpoint;

    @Scheduled(fixedRate = 10000)
    public void scheduler() {
        if (schedulerEndpoint != null && !schedulerEndpoint.isEmpty()) {
            log.info("Scheduling call");
            webClient.get()
                    .uri(schedulerEndpoint + "/endpoint")
                    .retrieve()
                    .bodyToMono(String.class)
                    .subscribe(s -> {
                        log.info("Received response from schedulerEndpoint: %s".formatted(s));
                    });
        }
    }

}
