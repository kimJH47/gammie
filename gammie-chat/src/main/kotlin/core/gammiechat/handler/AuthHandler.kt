package core.gammiechat.handler

import core.gammiechat.application.TokenValidator
import core.gammiechat.exception.ChatException
import core.gammiechat.exception.ErrorCode
import io.netty.channel.ChannelHandler.Sharable
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInboundHandlerAdapter
import io.netty.handler.codec.http.HttpRequest
import org.springframework.stereotype.Component
import org.springframework.web.util.UriComponentsBuilder

@Component
@Sharable
class AuthHandler(
    private val tokenValidator: TokenValidator
) : ChannelInboundHandlerAdapter() {


    override fun channelRead(ctx: ChannelHandlerContext, msg: Any) {
        if (msg is HttpRequest) {

            val uri = msg.uri()
            val component = UriComponentsBuilder.fromUriString(uri)
                .build()

            val token = component.queryParams.getFirst("token")
                ?: throw ChatException(ErrorCode.INVALID_TOKEN)

            val payload = tokenValidator.validateAndGetPayload(token)
            ctx.channel().attr(ConnectionAttributes.ROOM_ID_KEY).set(payload["roomId"].toString())
            ctx.channel().attr(ConnectionAttributes.USER_ID_KEY).set(payload["userId"].toString())
        }

        ctx.fireChannelRead(msg)
    }

}