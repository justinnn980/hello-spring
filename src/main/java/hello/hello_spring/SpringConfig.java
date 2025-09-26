package hello.hello_spring;

import hello.hello_spring.repository.MemoryNumberRepository;
import hello.hello_spring.repository.NumberRepository;
import hello.hello_spring.service.NumberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    @Bean
    public NumberService numberService() {
        return new NumberService(numberRepository());
    }

    @Bean
    public NumberRepository numberRepository() {
        return new MemoryNumberRepository();
    }
}
