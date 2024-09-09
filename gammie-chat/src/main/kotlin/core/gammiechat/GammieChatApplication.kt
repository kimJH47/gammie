package core.gammiechat

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class GammieChatApplication

fun main(args: Array<String>) {
    runApplication<GammieChatApplication>(*args)
}
