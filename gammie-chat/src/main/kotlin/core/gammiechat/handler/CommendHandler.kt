package core.gammiechat.handler

import core.gammiechat.application.*
import core.gammiechat.application.Command.CHAT_REQUEST
import core.gammiechat.application.Command.CONNECT
import core.gammiechat.logger
import io.netty.channel.ChannelHandler.Sharable
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler
import org.springframework.stereotype.Component

@Component
@Sharable
class CommendHandler(
    private val connectionService: ConnectionService,
    private val chattingService: ChattingService,
    private val pubsubService: PubSubService,
    private val validator: Validator,
) : SimpleChannelInboundHandler<Payload>() {
    private val log = logger()

    override fun channelRead0(ctx: ChannelHandlerContext, payload: Payload) {
        when (payload.command) {
            CONNECT -> {
                val request = validator.validateAndGet(payload.body, ConnectRequest::class)
                connectionService.connect(ctx.channel(), request)
                pubsubService.receiveMessage(request, ctx)
            }

            CHAT_REQUEST -> {
                chattingService.processMessage(validator.validateAndGet(payload.body, MessageRequest::class))
                    .subscribe({
                        pubsubService.sendMessage(validator.validateAndGet(payload.body, MessageRequest::class))
                    })
            }

            else -> {
                log.error("command not found, channel closed: {}", ctx.channel().id().asLongText())
                throw CustomSocketException(ErrorCode.COMMAND_NOT_FOUND)
            }
        }

    }
}