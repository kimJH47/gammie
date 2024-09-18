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
            .map {
                log.info("message : ${it.message}")
                MapperUtils.readJsonValueOrThrow(it.message.toString(), ChatDto::class)
            }
            .subscribe { it ->
                ctx.channel().writeAndFlush(CustomResponse(ResponseType.RECEIVE_CHAT, it))
                    .addListener {
                        if (it.isSuccess) {
                            log.info("write channel {}", ctx.channel().id())
                        } else {
                            log.warn("Failed to send the message to the client. roomId:${channel}, message:${it.cause().message}")
                        }
                    }
            }
    }
}