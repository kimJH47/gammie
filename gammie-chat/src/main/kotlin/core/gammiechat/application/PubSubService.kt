package core.gammiechat.application

import core.gammiechat.util.MapperUtils
import io.netty.channel.ChannelHandlerContext
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class PubSubService(
    private val redisMessagePublisher: RedisMessagePublisher,
    private val redisMessageSubscriber: RedisMessageSubscriber
) {

    fun sendMessage(chatDto: ChatDto) {
        redisMessagePublisher.publish("$TOPIC_PREFIX:${chatDto.roomId}", MapperUtils.toJson(chatDto))
    }

    fun sub(request: ConnectionRequest, ctx: ChannelHandlerContext): Mono<Unit> {
        redisMessageSubscriber.subscribe("$TOPIC_PREFIX:${request.roomId}", ctx)
        return Mono.empty()
    }

    companion object {
        const val TOPIC_PREFIX = "chatroom"
    }
}
