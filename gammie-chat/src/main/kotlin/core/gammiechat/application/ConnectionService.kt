package core.gammiechat.application

import core.gammiechat.application.dto.DisconnectRequest
import io.netty.channel.ChannelHandlerContext
import org.springframework.stereotype.Service

@Service
class ConnectionService(
    private val chatRoomClient: ChatRoomClient
) {

    fun connect(ctx: ChannelHandlerContext, request: ConnectionRequest) {
        chatRoomClient.addParticipant(request.roomId, request.userId)
            .doOnError {
                ctx.close()
            }
            .subscribe()
    }

    fun disconnect(ctx: ChannelHandlerContext, request: DisconnectRequest) {
        ctx.channel().closeFuture().addListener {
            chatRoomClient.exitChatRoom(request.roomId, request.userId)
                .subscribe()
        }
    }
}
