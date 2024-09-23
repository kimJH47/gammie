package core.gammieapi.application

import core.gammieapi.repository.ChatRoomRepository
import core.gammieapi.repository.UserParticipantRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*
import kotlin.time.DurationUnit
import kotlin.time.toDuration

@Service
class ChatRoomService(
    private val chatRoomRepository: ChatRoomRepository,
    private val chatTokenProvider: ChatTokenProvider,
    private val userParticipantRepository: UserParticipantRepository
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

    fun join(roomId: String, userId: String): JoinResponse {
        if (!chatRoomRepository.existsById(UUID.fromString(roomId))) {
            throw CustomException(ErrorCode.CHAT_ROOM_NOT_FOUND)
        }
        val chatToken = chatTokenProvider.provide(
            roomId, userId, 30.toDuration(DurationUnit.SECONDS).toInt(DurationUnit.MILLISECONDS)
        )
        return JoinResponse(chatToken, roomId, userId)
    }

    @Transactional
    fun exit(roomId: String, userId: String) {
        chatRoomRepository.decreaseJoinCount(roomId)
        userParticipantRepository.deleteByUserIdAndRoomId(roomId, UUID.fromString(userId))
    }
}