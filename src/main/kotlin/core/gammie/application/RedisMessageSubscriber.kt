package core.gammie.application

import core.gammie.logger
import io.netty.channel.ChannelHandlerContext
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.stereotype.Service

@Service
class RedisMessageSubscriber(
    private val redisTemplate: ReactiveRedisTemplate<String, Any>
) {
    private val log = logger()

    fun subscribe(channel: String, ctx: ChannelHandlerContext) {
        redisTemplate.listenToChannel(channel)
            .map { it.message }
            .subscribe {
                ctx.writeAndFlush(it)
                log.info("Subscribed to channel {}", channel)
            }
    }
}