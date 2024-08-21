package core.gammie.handler

import core.gammie.logger
import io.netty.channel.ChannelDuplexHandler
import io.netty.channel.ChannelHandler
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.http.websocketx.PingWebSocketFrame
import io.netty.handler.timeout.IdleState
import io.netty.handler.timeout.IdleStateEvent
import org.springframework.stereotype.Component

@Component
@ChannelHandler.Sharable
class IdleHandler : ChannelDuplexHandler() {
    val log = logger()
    override fun userEventTriggered(ctx: ChannelHandlerContext, evt: Any) {
        if (evt is IdleStateEvent &&
            evt.state() == IdleState.ALL_IDLE
        ) {
            log.info("wake up ide channel:{}", ctx.channel().remoteAddress())
            ctx.writeAndFlush(PingWebSocketFrame())
            return
        }
        super.userEventTriggered(ctx, evt)
    }
}
