package core.gammiechat.handler

import core.gammiechat.application.dto.CustomResponse
import core.gammiechat.util.MapperUtils
import io.netty.channel.ChannelHandler.Sharable
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.MessageToMessageEncoder
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame
import org.springframework.stereotype.Component

@Component
@Sharable
class WebCustomResponseEncoder : MessageToMessageEncoder<CustomResponse>() {

    override fun encode(ctx: ChannelHandlerContext, response: CustomResponse, out: MutableList<Any>) {
        MapperUtils.toJson(response).run {
            out.add(TextWebSocketFrame(this))
        }
    }
}
