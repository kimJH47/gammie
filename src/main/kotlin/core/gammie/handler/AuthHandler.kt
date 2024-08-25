package core.gammie.handler

import core.gammie.application.Payload
import core.gammie.application.TokenValidator
import io.netty.channel.ChannelHandler.Sharable
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler
import org.springframework.stereotype.Component

@Component
@Sharable
class AuthHandler(
    private val tokenValidator: TokenValidator
) : SimpleChannelInboundHandler<Payload>() {

    override fun channelRead0(ctx: ChannelHandlerContext, paylaod: Payload) {
        tokenValidator.validate(paylaod.token)
        ctx.fireChannelRead(paylaod)
    }
}