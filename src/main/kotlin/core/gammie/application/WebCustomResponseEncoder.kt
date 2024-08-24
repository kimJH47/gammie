package core.gammie.application

import core.gammie.util.MapperUtils
import io.netty.channel.ChannelHandler.Sharable
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.MessageToMessageEncoder
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame
import org.springframework.stereotype.Component

@Component
@Sharable
class WebCustomResponseEncoder : MessageToMessageEncoder<CustomResponse>() {

    override fun encode(ctx: ChannelHandlerContext, response: CustomResponse, out: MutableList<Any>) {
        MapperUtils.toJson(response).let {
            out.add(TextWebSocketFrame(it))
        }
    }
}
