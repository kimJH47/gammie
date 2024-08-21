package core.gammie.application

import com.fasterxml.jackson.databind.ObjectMapper
import io.netty.channel.ChannelHandler
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.MessageToMessageDecoder
import org.springframework.stereotype.Component

@Component
@ChannelHandler.Sharable
class PayloadDecoder : MessageToMessageDecoder<String>() {

    private val objectMapper = ObjectMapper()

    override fun decode(p0: ChannelHandlerContext, message: String, out: MutableList<Any>) {
        objectMapper.readValue(message, Payload::class.java)?.let {
            out.add(it)
        }
    }
}
