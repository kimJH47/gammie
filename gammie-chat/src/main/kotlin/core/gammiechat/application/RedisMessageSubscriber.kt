package core.gammiechat.application

import core.gammiechat.logger
import core.gammiechat.util.MapperUtils
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
            .map { MapperUtils.readValueOrThrow(it.message, ChatDto::class) }
            .subscribe {
                ctx.writeAndFlush(CustomResponse(ResponseType.RECEIVE_CHAT, it))
                log.info("Subscribed to channel {}", channel)
            }
    }
}