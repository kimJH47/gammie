package core.gammiechat.handler

import core.gammiechat.application.*
import core.gammiechat.application.Command.CHAT_REQUEST
import core.gammiechat.logger
import io.netty.channel.ChannelHandler.Sharable
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler
import org.springframework.stereotype.Component

@Component
@Sharable
class CommendHandler(
    private val chattingService: ChattingService,
    private val pubsubService: PubSubService,
    private val validator: Validator,
) : SimpleChannelInboundHandler<Payload>() {
    private val log = logger()

    override fun channelRead0(ctx: ChannelHandlerContext, payload: Payload) {
        when (payload.command) {
            CHAT_REQUEST -> {
                chattingService.processChatDto(validator.validateAndGet(payload.body, MessageRequest::class))
                    .subscribe { pubsubService.sendMessage(it) }
            }

            else -> {
                log.error("command not found, channel closed: {}", ctx.channel().id().asLongText())
                throw CustomSocketException(ErrorCode.COMMAND_NOT_FOUND)
            }
        }

    }
}