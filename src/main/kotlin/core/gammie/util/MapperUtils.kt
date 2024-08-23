package core.gammie.util

import com.fasterxml.jackson.databind.ObjectMapper

class MapperUtils private constructor() {
    companion object {
        private val objectMapper = ObjectMapper()

        fun <T> readValueOrThrow(value: Any, type: Class<T>): T {
            try {
                return objectMapper.convertValue(value, type)
            } catch (e : IllegalArgumentException) {
                throw e
            }
        }
    }
}