package core.gammiechat.application

import core.gammiechat.logger
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.stereotype.Service

@Service
class RedisMessagePublisher(
    private val reactiveRedisTemplate: ReactiveRedisTemplate<String, Any>
) {

    private val log = logger()

    fun publish(topic: String, message: String) {
        reactiveRedisTemplate.convertAndSend(topic, message)
            .subscribe {
                log.info("published topic: $topic, message: $message")
            }
    }
}
