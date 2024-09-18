package core.gammiechat.util

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import kotlin.reflect.KClass

class MapperUtils private constructor() {
    companion object {
        private val objectMapper = ObjectMapper().registerKotlinModule()


        fun <T : Any> readValueOrThrow(value: Any, type: KClass<T>): T {
            try {
                return objectMapper.convertValue(value, type.java)
            } catch (e: IllegalArgumentException) {
                throw e
            }
        }

        fun <T : Any> readJsonValueOrThrow(json: String, type: KClass<T>): T {
            try {
                return objectMapper.readValue(json, type.java)
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