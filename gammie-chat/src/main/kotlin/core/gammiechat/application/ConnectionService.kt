package core.gammiechat.application

import core.gammiechat.application.dto.ChatDto
import core.gammiechat.application.dto.ConnectionRequest
import core.gammiechat.application.dto.DisconnectRequest
import io.netty.channel.ChannelHandlerContext
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.scheduler.Schedulers

@Service
class ConnectionService(
    private val chatRoomClient: ChatRoomClient,
    private val pubSubService: PubSubService,
) {

    fun connect(ctx: ChannelHandlerContext, request: ConnectionRequest): Flux<ChatDto> {
        return pubSubService.sub(ctx, request.roomId)
            .doOnSubscribe {
                chatRoomClient.addParticipant(request.roomId, request.userId)
                    .subscribeOn(Schedulers.boundedElastic())
                    .subscribe()
            }
    }

    fun disconnect(ctx: ChannelHandlerContext, request: DisconnectRequest) {
        ctx.channel().closeFuture().addListener {
            chatRoomClient.exitChatRoom(request.roomId, request.userId)
                .subscribe()
        }
    }
}
