package core.gammieapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class GammieApiApplication

fun main(args: Array<String>) {
    runApplication<GammieApiApplication>(*args)
}
