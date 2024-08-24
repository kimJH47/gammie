package core.gammie.repository

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component

@Component
class ConnectionRedisRepository(
    private val redisTemplate: RedisTemplate<String, Any>
) {
    fun createConnection(roomId: String, channelId: String) {
        val opsForSet = redisTemplate.opsForSet()
        opsForSet.add(roomId, channelId)
    }
}