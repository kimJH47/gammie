package core.gammiechat.application

import core.gammiechat.application.dto.ChatDto
import core.gammiechat.application.dto.CustomResponse
import core.gammiechat.application.dto.ResponseType
import core.gammiechat.logger
import core.gammiechat.util.MapperUtils
import io.netty.channel.ChannelHandlerContext
import org.springframework.data.redis.listener.ChannelTopic
import org.springframework.data.redis.listener.ReactiveRedisMessageListenerContainer
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.stereotype.Service

@Service
class RedisMessageSubscriber(
    private val reactiveRedisMessageListenerContainer: ReactiveRedisMessageListenerContainer,
    private val serializationContext: RedisSerializationContext<String, Any>,

    ) {
    private val log = logger()

    fun subscribe(channel: String, ctx: ChannelHandlerContext) {
        val disposable = reactiveRedisMessageListenerContainer.receive(
            arrayListOf(ChannelTopic.of(channel)),
            serializationContext.keySerializationPair, serializationContext.valueSerializationPair
        )
            .map { MapperUtils.readJsonValueOrThrow(it.message.toString(), ChatDto::class) }
            .doOnNext { it ->
                ctx.channel().writeAndFlush(CustomResponse(ResponseType.RECEIVE_CHAT, it))
                    .addListener {
                        if (it.isSuccess) {
                            log.info("write channel topic: $channel")
                        } else {
                            ctx.channel().close()
                            log.warn("Failed to send the message to the client. roomId:${channel}, message:${it.cause()}, socketOpen: ${ctx.channel().isOpen}")
                        }
                    }
            }
            .doOnCancel {
                log.info("disconnecting redis channel: $channel")
            }.subscribe()

        ctx.channel().attr(ConnectionAttributes.SUBSCRIPTION_KEY).set(disposable)
    }
}