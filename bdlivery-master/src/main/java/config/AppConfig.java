package config;

import repositories.DBliveryRepository;
import services.DBliveryService;
import services.DBliveryServiceImpl;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public DBliveryService createService() {
        DBliveryRepository repository = this.createRepository();
        return new DBliveryServiceImpl(repository);
    }

    @Bean
    public DBliveryRepository createRepository() {
        return new DBliveryRepository();
    }
}
