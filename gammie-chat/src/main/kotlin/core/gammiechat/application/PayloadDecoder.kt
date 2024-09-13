package core.gammiechat.application

import core.gammiechat.util.MapperUtils
import io.netty.channel.ChannelHandler.Sharable
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.MessageToMessageDecoder
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame
import org.springframework.stereotype.Component

@Component
@Sharable
class PayloadDecoder : MessageToMessageDecoder<TextWebSocketFrame>() {

    override fun decode(ctx: ChannelHandlerContext, message: TextWebSocketFrame, out: MutableList<Any>) {
        MapperUtils.jsonToPayloadOrThrow(message.text()).let {
            out.add(it)
        }
    }
}
