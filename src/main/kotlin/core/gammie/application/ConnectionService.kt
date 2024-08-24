package core.gammie.application

import core.gammie.logger
import core.gammie.repository.ConnectionRedisRepository
import io.netty.channel.Channel
import org.springframework.stereotype.Service

@Service
class ConnectionService(
    private val connectionRedisRepository: ConnectionRedisRepository
) {
    private val log = logger()

    fun connect(channel: Channel, request: ConnectRequest) {
        //채팅방 id 검증
        validateChatRoomId(request.roomId)
        connectionRedisRepository.createConnection(request.roomId, channel.id().asLongText())
        channel.writeAndFlush(CustomResponse(ResponseType.CONNECTED))
            .addListener {
                if (it.isSuccess) {
                    log.info("Message was successfully sent to the client.")
                } else {
                    log.info("Failed to send the message to the client.")
                    it.cause().printStackTrace()
                }
            }
    }

    private fun validateChatRoomId(roomId: String) {
        //채팅방 id 가 존재하는지 검증
    }
}
