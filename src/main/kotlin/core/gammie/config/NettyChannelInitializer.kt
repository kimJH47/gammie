package core.gammie.config

import core.gammie.application.PayloadDecoder
import core.gammie.application.WebCustomResponseEncoder
import core.gammie.handler.AuthHandler
import core.gammie.handler.CommendHandler
import core.gammie.handler.IdleHandler
import io.netty.channel.ChannelInitializer
import io.netty.channel.ChannelPipeline
import io.netty.channel.socket.SocketChannel
import io.netty.handler.codec.http.HttpObjectAggregator
import io.netty.handler.codec.http.HttpServerCodec
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketServerCompressionHandler
import io.netty.handler.timeout.IdleStateHandler
import org.springframework.stereotype.Component

@Component
class NettyChannelInitializer(
    private val idleHandler: IdleHandler,
    private val payloadDecoder: PayloadDecoder,
    private val commendHandler: CommendHandler,
    private val authHandler: AuthHandler,
    private val webCustomResponseEncoder : WebCustomResponseEncoder

) : ChannelInitializer<SocketChannel>() {

    override fun initChannel(socketChannel: SocketChannel) {
        val pipeline: ChannelPipeline = socketChannel.pipeline()
        pipeline.addLast(HttpServerCodec())
            .addLast(HttpObjectAggregator(65536))
            .addLast(WebSocketServerCompressionHandler())
            .addLast(WebSocketServerProtocolHandler("/ws", null, true))
            .addLast(IdleStateHandler(0, 0, 180))
            .addLast(idleHandler)
            .addLast(payloadDecoder)
            .addLast(webCustomResponseEncoder)
            .addLast(authHandler)
            .addLast(commendHandler)
        //        .addLast(HealthCheckHandler())
    }
}
