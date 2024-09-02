package core.gammieapi.application

import core.gammieapi.repository.ChatRoomRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
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
            chatRoom.genres
        )
    }

    fun findWithLastId(lastId: String): ChatRoomPageResponse {
        val uuid = UUID.fromString(lastId)

        val chatRooms =
            chatRoomRepository.findByIdGreaterThan(uuid, getMaxSizePageable())
                .takeIf { it.isNotEmpty() }
                ?.map {
                    ChatRoomResponse(it.id.toString(), it.name, it.description, it.joinCount, it.imageUrl, it.genres)
                } ?: return ChatRoomPageResponse("", emptyList())

        return ChatRoomPageResponse(chatRooms.last().id, chatRooms)
    }

    fun findRecent(): ChatRoomPageResponse {
        val chatRooms = chatRoomRepository.findAllBy(getMaxSizePageable())
            .takeIf { it.isNotEmpty() }
            ?.map {
                ChatRoomResponse(it.id.toString(), it.name, it.description, it.joinCount, it.imageUrl, it.genres)
            } ?: return ChatRoomPageResponse("", emptyList())

        return ChatRoomPageResponse(chatRooms.last().id, chatRooms)

    }

    private fun getMaxSizePageable(): Pageable {
        return PageRequest.of(0, 10)
    }
}