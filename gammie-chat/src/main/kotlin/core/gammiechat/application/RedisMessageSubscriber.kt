package core.gammiechat.application

import core.gammiechat.application.dto.ChatDto
import core.gammiechat.application.dto.CustomResponse
import core.gammiechat.application.dto.ResponseType
import core.gammiechat.exception.ChatException
import core.gammiechat.exception.ErrorCode
import core.gammiechat.logger
import core.gammiechat.util.MapperUtils
import io.netty.channel.ChannelHandlerContext
import org.springframework.data.redis.listener.ChannelTopic
import org.springframework.data.redis.listener.ReactiveRedisMessageListenerContainer
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class RedisMessageSubscriber(
    private val reactiveRedisMessageListenerContainer: ReactiveRedisMessageListenerContainer,
    private val serializationContext: RedisSerializationContext<String, Any>
) {
    private val log = logger()

    fun subscribe(channel: String, ctx: ChannelHandlerContext): Flux<ChatDto> {
        return reactiveRedisMessageListenerContainer.receive(
            createTopic(channel),
            serializationContext.keySerializationPair, serializationContext.valueSerializationPair
        ).doOnSubscribe {
            log.info("Subscribing to topic {}", channel)
        }.map {
            MapperUtils.readJsonValueOrThrow(it.message.toString(), ChatDto::class)
        }.flatMap {
            Mono.create<Unit> { sink ->
                ctx.channel().writeAndFlush(CustomResponse(ResponseType.RECEIVE_CHAT, it))
                    .addListener { future ->
                        if (future.isSuccess) {
                            log.info("write channel topic: $channel")
                            sink.success()
                        } else {
                            log.warn("Failed to send the message to the client. roomId:${channel}")
                            sink.error(ChatException(ErrorCode.FAIL_SEND_MESSAGE))
                        }
                    }
            }.thenReturn(it)
        }.onErrorResume {
            ctx.channel().close()
            log.error("close redis listener roomId:${channel}, message:${it.message}, socketOpen: ${ctx.channel().isOpen}")
            Mono.empty()
        }
    }

    private fun createTopic(channel: String) = arrayListOf(ChannelTopic.of(channel))
}