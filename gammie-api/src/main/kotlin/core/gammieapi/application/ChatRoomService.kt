package core.gammieapi.application

import core.gammieapi.repository.ChatRoomRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class ChatRoomService(
    private val chatRoomRepository: ChatRoomRepository,
) {

    @Transactional(readOnly = true)
    fun findOne(chatRoomId: String): ChatRoomResponse {
        val chatRoom = chatRoomRepository.findById(UUID.fromString(chatRoomId))
            .orElseThrow { CustomException(ErrorCode.CHAT_ROOM_NOT_FOUND) }

        return ChatRoomResponse(
            chatRoom.id.toString(),
            chatRoom.name,
            chatRoom.description,
            chatRoom.joinCount,
            chatRoom.imageUrl,
            chatRoom.category
        )
    }
}