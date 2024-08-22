package core.gammie.application

import core.gammie.logger
import io.netty.bootstrap.ServerBootstrap
import jakarta.annotation.PreDestroy
import org.springframework.stereotype.Component
import java.net.InetSocketAddress

@Component
class NettyServerSocket(
    private val bootstrap: ServerBootstrap,
    private val tcp: InetSocketAddress
) {
    private val log = logger()

    fun start() {
        try {
            log.info("Netty server started")
            val channelFuture = bootstrap.bind(tcp)
                .sync()
            channelFuture.channel().closeFuture().sync()

        } catch (ex: InterruptedException) {
            log.error("Netty server interrupted", ex)
        }
    }

    @PreDestroy
    fun stop() {
        log.info("Netty server stopped")
    }
}


