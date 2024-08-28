package core.gammieapi.application

import core.gammieapi.repository.ChatRoomRepository
import org.springframework.stereotype.Service

@Service
class ChatRoomService(
    private val chatRoomRepository: ChatRoomRepository,
) {
    fun findOne(chatRoomId: Long): ChatRoomResponse {
        val chatRoom = chatRoomRepository.findById(chatRoomId)
            .orElseThrow { CustomException(ErrorCode.CHAT_ROOM_NOT_FOUND) }

        return ChatRoomResponse(
            chatRoom.id.toString(),
            chatRoom.name,
            chatRoom.description,
            chatRoom.joinCount,
            chatRoom.imageUrl
        )
    }
}