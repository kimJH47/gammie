package core.gammiechat.config

import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.ChannelOption
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioServerSocketChannel
import io.netty.handler.logging.LoggingHandler
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.net.InetSocketAddress

@Configuration
class NettyConfig(
    @Value("\${server.host}")
    private val host: String,
    @Value("\${server.port}")
    private val port: Int,
    @Value("\${server.netty.boss-count}")
    private val bossCount: Int,
    @Value("\${server.netty.worker-count}")
    private val workerCount: Int,
    @Value("\${server.netty.keep-alive}")
    private val keepAlive: Boolean,
    @Value("\${server.netty.backlog}")
    private val backlog: Int
) {

    @Bean
    fun serverBootstrap(nettyChannelInitializer: NettyChannelInitializer): ServerBootstrap {
        val bootstrap = ServerBootstrap()
        bootstrap.group(bossGroup(), workerGroup())
            .channel(NioServerSocketChannel::class.java)
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000)
            //.childOption(ChannelOption.TCP_NODELAY, true) // [5]
            //.childOption(ChannelOption.SO_LINGER, 0) // [6]
            //.childOption(ChannelOption.SO_KEEPALIVE, keepAlive) // [7]
            //.option(ChannelOption.SO_BACKLOG, backlog) // [4]
            //.childOption(ChannelOption.SO_REUSEADDR, true) // [8]
            .handler(LoggingHandler())
            .childHandler(nettyChannelInitializer)

        return bootstrap
    }

    @Bean(destroyMethod = "shutdownGracefully")
    fun bossGroup(): NioEventLoopGroup {
        return NioEventLoopGroup(bossCount)
    }

    @Bean(destroyMethod = "shutdownGracefully")
    fun workerGroup(): NioEventLoopGroup {
        return NioEventLoopGroup(workerCount)
    }

    @Bean
    fun inetSocketAddress(): InetSocketAddress {
        return InetSocketAddress(host, port)
    }
}
