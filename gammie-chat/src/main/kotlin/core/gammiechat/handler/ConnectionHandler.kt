package core.gammiechat.handler

import core.gammiechat.application.ConnectionAttributes
import core.gammiechat.application.ConnectionService
import core.gammiechat.application.dto.ConnectionRequest
import core.gammiechat.application.dto.DisconnectRequest
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
            val payload = ctx.channel().attr(ConnectionAttributes.CHAT_PAYLOAD_KEY).get()
            val request = ConnectionRequest(payload.roomId, payload.userId)
            connectionService.connect(ctx, request)
                .subscribe()
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
