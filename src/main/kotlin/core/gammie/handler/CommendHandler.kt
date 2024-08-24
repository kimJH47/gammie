package core.gammie.handler

import core.gammie.application.*
import core.gammie.logger
import io.netty.channel.ChannelHandler.Sharable
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler
import org.springframework.stereotype.Component

@Component
@Sharable
class CommendHandler(
    private val connectionService: ConnectionService,
    private val validator: Validator,
) : SimpleChannelInboundHandler<Payload>() {
    private val log = logger()

    override fun channelRead0(ctx: ChannelHandlerContext, payload: Payload) {
        when (payload.command) {
            Command.CONNECT -> {
                connectionService.connect(
                    ctx.channel(), validator.validateAndGet(payload.body, ConnectRequest::class)
                )
            }

            else -> {
                log.error("command not found, channel closed: {}", ctx.channel().id().asLongText())
                throw CustomSocketException(ErrorCode.COMMAND_NOT_FOUND)
            }
        }

    }
}