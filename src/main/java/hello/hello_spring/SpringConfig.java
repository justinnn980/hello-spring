package hello.hello_spring;

import hello.hello_spring.repository.MemoryNumberRepository;
import hello.hello_spring.repository.NumberRepository;
import hello.hello_spring.service.NumberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@Configuration
public class SpringConfig {

    @Bean
    public NumberRepository numberRepository() {
        return new MemoryNumberRepository();
    }

    @Bean
    public NumberService numberService(NumberRepository numberRepository,
        SimpMessagingTemplate messagingTemplate) {
        return new NumberService(numberRepository, messagingTemplate);
    }
}
