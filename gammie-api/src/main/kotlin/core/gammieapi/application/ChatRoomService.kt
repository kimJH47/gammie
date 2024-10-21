package core.gammieapi.application

import core.gammieapi.repository.ChatRoomRepository
import core.gammieapi.repository.UserParticipant
import core.gammieapi.repository.UserParticipantRepository
import core.gammieapi.repository.UserRepository
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
    private val userParticipantRepository: UserParticipantRepository,
    private val userRepository: UserRepository,
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

    //트랜잭션 분리해야함.
    @Transactional
    fun exit(roomId: String, userId: String) {
        chatRoomRepository.findAndDecrementJoinCountById(UUID.fromString(roomId))
        userParticipantRepository.deleteByUserIdAndRoomId(roomId, UUID.fromString(userId))
    }

    @Transactional
    fun addParticipant(roomId: String, userId: String) {
        validateChatRoom(roomId)
        validateUser(userId)
        userParticipantRepository.save(UserParticipant(userId, UUID.fromString(roomId)))
        chatRoomRepository.findAndIncrementJoinCountById(UUID.fromString(roomId))
    }


    private fun validateChatRoom(roomId: String) {
        if (!chatRoomRepository.existsById(UUID.fromString(roomId))) {
            throw CustomException(ErrorCode.CHAT_ROOM_NOT_FOUND)
        }
    }

    private fun validateUser(nickname: String) {
        //userId == nickname
        if (!userRepository.existsByNickname(nickname)) {
            throw CustomException(ErrorCode.USER_NOT_FOUND)
        }
    }

    fun findByName(query: String): ChatRoomPageResponse {
        val chatRooms = chatRoomRepository.findByName(query).takeIf { it.isNotEmpty() }
            ?.map {
                ChatRoomResponse(it.id.toString(), it.name, it.description, it.joinCount, it.imageUrl, it.genres)
            } ?: return ChatRoomPageResponse("", emptyList())

        return ChatRoomPageResponse(chatRooms.last().id, chatRooms)
    }
}