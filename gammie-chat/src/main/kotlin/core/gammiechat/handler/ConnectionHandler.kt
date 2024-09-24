package core.gammiechat.handler

import core.gammiechat.application.ConnectionRequest
import core.gammiechat.application.ConnectionService
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
    private val pubSubService: PubSubService,
    private val connectionService: ConnectionService
) : ChannelInboundHandlerAdapter() {

    private val logger = logger()

    override fun userEventTriggered(ctx: ChannelHandlerContext, evt: Any) {
        if (evt is HandshakeComplete) {
            val userId = ctx.channel().attr(ConnectionAttributes.USER_ID_KEY).get()
            val roomId = ctx.channel().attr(ConnectionAttributes.ROOM_ID_KEY).get()
            val request = ConnectionRequest(roomId, userId)
            pubSubService.sub(request, ctx)
                .subscribe {
                    logger.info(
                        "connection channel subscribe. channelId: ${
                            ctx.channel().id()
                        }, roomId: $roomId, userId: $userId"
                    )
                }
            connectionService.connect(ctx, ConnectionRequest(roomId, userId))
        }
    }

    override fun channelInactive(ctx: ChannelHandlerContext) {
        val disposable = ctx.channel().attr(ConnectionAttributes.SUBSCRIPTION_KEY).get()
        if (!disposable.isDisposed) {
            disposable.dispose()
        }
        logger.info("channel unsubscribe.")
        ctx.fireChannelInactive()
    }
}
