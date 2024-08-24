package core.gammie.application

import core.gammie.logger
import core.gammie.util.MapperUtils
import jakarta.validation.ConstraintViolationException
import jakarta.validation.Validator
import org.springframework.stereotype.Service
import kotlin.reflect.KClass

@Service
class Validator(
    private val validator: Validator,
) {
    private val log = logger()

    fun <T : Any> validateAndGet(body: Any, classType: KClass<T>): T {
        val value = MapperUtils.readValueOrThrow(body, classType)
        validate(value)
        return value
    }

    private fun <T> validate(value: T) {
        val validations = validator.validate(value)
        if (validations.isNotEmpty()) {
            val constraintViolation = validations.stream()
                .findFirst()
                .orElse(null)
            log.error("validate error {}", constraintViolation.message)
            throw ConstraintViolationException(validations)
        }
    }
}
