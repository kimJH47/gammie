package core.gammie.util

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import core.gammie.application.Payload
import kotlin.reflect.KClass

class MapperUtils private constructor() {
    companion object {
        private val objectMapper = jacksonObjectMapper()

        fun <T : Any> readValueOrThrow(value: Any, type: KClass<T>): T {
            try {
                return objectMapper.convertValue(value, type.java)
            } catch (e: IllegalArgumentException) {
                throw e
            }
        }

        fun jsonToPayloadOrThrow(json: String): Payload {
            try {
                return objectMapper.readValue<Payload>(json)
            } catch (e: IllegalArgumentException) {
                throw e
            }
        }

        fun toJson(response: Any): String {
            try {
                return objectMapper.writeValueAsString(response)
            } catch (e: IllegalArgumentException) {
                throw e
            }
        }
    }
}