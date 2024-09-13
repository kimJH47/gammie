package core.gammiechat.application

import io.netty.channel.ChannelHandlerContext
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class PubSubService(
    private val redisMessagePublisher: RedisMessagePublisher,
    private val redisMessageSubscriber: RedisMessageSubscriber
) {

    fun sendMessage(request: MessageRequest) {
        redisMessagePublisher.publish("chatroom:${request.roomId}", request.content)
    }

    fun sub(request: ConnectRequest, ctx: ChannelHandlerContext) : Mono<Unit> {
        redisMessageSubscriber.subscribe("chatroom:${request.roomId}", ctx)
        return Mono.empty()
    }
}
