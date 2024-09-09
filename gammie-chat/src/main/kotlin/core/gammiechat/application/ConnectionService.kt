package core.gammiechat.application

import core.gammiechat.handler.CustomSocketException
import core.gammiechat.handler.ErrorCode
import core.gammiechat.logger
import core.gammiechat.repository.ConnectionRedisRepository
import io.netty.channel.Channel
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class ConnectionService(
    private val connectionRedisRepository: ConnectionRedisRepository,
    private val chatRoomClient: ChatRoomClient
) {
    private val log = logger()

    fun connect(channel: Channel, request: ConnectRequest) {
        getChatRoomById(request.roomId)
            .subscribe(
                {
                    connectionRedisRepository.createConnection(
                        request.roomId,
                        channel.id().asLongText(),
                        request.userId
                    )
                    channel.writeAndFlush(CustomResponse(ResponseType.CONNECTED))
                        .addListener {
                            if (it.isSuccess) {
                                log.info("Message was successfully sent to the client. channel id:${channel.id()}")
                            } else {
                                log.warn("Failed to send the message to the client. channel id:${channel.id()}, message:${it.cause().message}")
                            }
                        }
                },
                {
                    log.error("Failed to connect to the client", it)
                    throw it
                }
            )
    }

    private fun getChatRoomById(roomId: String): Mono<ChatRoomResponse> {
        return chatRoomClient.asyncFindChatRoomById(roomId)
            .onErrorMap {
                throw CustomSocketException(ErrorCode.CHAT_ROOM_NOT_FOUND)
            }
    }
}
