package core.gammiechat.handler

import core.gammiechat.application.ConnectRequest
import core.gammiechat.application.PubSubService
import core.gammiechat.logger
import io.netty.channel.ChannelHandler.Sharable
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInboundHandlerAdapter
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler.HandshakeComplete
import org.springframework.stereotype.Component

@Component
@Sharable
class ConnectionHandler(
    private val pubSubService: PubSubService
) : ChannelInboundHandlerAdapter() {

    private val logger = logger()

    override fun userEventTriggered(ctx: ChannelHandlerContext, evt: Any) {
        if (evt is HandshakeComplete) {
            val userId = ctx.channel().attr(ConnectionAttributes.USER_ID_KEY).get()
            val roomId = ctx.channel().attr(ConnectionAttributes.ROOM_ID_KEY).get()
            val request = ConnectRequest(roomId, userId)
            pubSubService.sub(request, ctx)
                .subscribe {
                    logger.info(
                        "connection channel subscribe. channelId: ${
                            ctx.channel().id()
                        }, roomId: $roomId, userId: $userId"
                    )
                }
        }
    }
}
