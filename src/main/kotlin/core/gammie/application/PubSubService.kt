package core.gammie.application

import io.netty.channel.ChannelHandlerContext
import org.springframework.stereotype.Service

@Service
class PubSubService(
    private val redisMessagePublisher: RedisMessagePublisher,
    private val redisMessageSubscriber: RedisMessageSubscriber
) {

    fun sendMessage(request: MessageRequest) {
        redisMessagePublisher.publish("chatroom:${request.roomId}", request.content)
    }

    fun receiveMessage(request: ConnectRequest, ctx: ChannelHandlerContext) {
        redisMessageSubscriber.subscribe("chatroom:${request.roomId}", ctx)
    }
}
