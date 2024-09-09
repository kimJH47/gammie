package core.gammiechat.application

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service

@Service
class RedisMessagePublisher(
    private val redisTemplate: RedisTemplate<String, Any>
) {

    fun publish(topic: String, message: String) {
        redisTemplate.convertAndSend("chatroom:${topic}", message)
    }
}
