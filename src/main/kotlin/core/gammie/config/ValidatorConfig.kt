package core.gammie.config

import jakarta.validation.Validation
import jakarta.validation.Validator
import org.hibernate.validator.HibernateValidator
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ValidatorConfig {

    @Bean
    fun hibernateValidator(): Validator {
        return Validation.byProvider(HibernateValidator::class.java)
            .configure()
            .buildValidatorFactory().validator
    }
}