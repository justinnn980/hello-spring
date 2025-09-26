package hello.hello_spring;

import hello.hello_spring.repository.JdbcNumberRepository;
import hello.hello_spring.repository.MemoryNumberRepository;
import hello.hello_spring.repository.NumberRepository;
import hello.hello_spring.service.NumberService;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    private DataSource dataSource;

    @Autowired
    public SpringConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public NumberService numberService() {
        return new NumberService(numberRepository());
    }

    @Bean
    public NumberRepository numberRepository() {
//        return new MemoryNumberRepository();
        return new JdbcNumberRepository(dataSource);
    }
}
