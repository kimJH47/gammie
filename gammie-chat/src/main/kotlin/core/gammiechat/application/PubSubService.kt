package core.gammiechat.application

import core.gammiechat.application.dto.ChatDto
import core.gammiechat.util.MapperUtils
import io.netty.channel.ChannelHandlerContext
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class PubSubService(
    private val redisMessagePublisher: RedisMessagePublisher,
    private val redisMessageSubscriber: RedisMessageSubscriber
) {

    fun sendMessage(chatDto: ChatDto) {
        redisMessagePublisher.publish("$TOPIC_PREFIX:${chatDto.roomId}", MapperUtils.toJson(chatDto))
    }

    fun sub(ctx: ChannelHandlerContext, roomId: String): Flux<ChatDto> {
        return redisMessageSubscriber.subscribe("$TOPIC_PREFIX:${roomId}", ctx)
    }

    companion object {
        const val TOPIC_PREFIX = "chatroom"
    }
}
