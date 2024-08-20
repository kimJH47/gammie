package core.gammie

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class GammieApplication

fun main(args: Array<String>) {
    runApplication<GammieApplication>(*args)
}
