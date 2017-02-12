package simple.authority.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import simple.authority.entity.base.UserAuditorAware;

@Configuration
@EnableJpaAuditing
public class AuditingConfiguration {

    @Bean
    UserAuditorAware auditorAware() {
        return new UserAuditorAware();
    }
}
