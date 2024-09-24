package core.gammieapi.presentation.http

import core.gammieapi.application.*
import core.gammieapi.auth.domain.AuthUser
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
class ChatRoomController(
    private val chatRoomService: ChatRoomService
) {

    @GetMapping("/api/chat-rooms/{id}")
    fun findChatRoom(@PathVariable("id") chatRoomId: String): ResponseEntity<ChatRoomResponse> {
        return ResponseEntity.ok(chatRoomService.findOne(chatRoomId))
    }

    @GetMapping("/api/chat-rooms")
    fun findAllChatRooms(@RequestParam("lastId") lastId: String): ResponseEntity<ChatRoomPageResponse> {
        return ResponseEntity.ok(chatRoomService.findWithLastId(lastId))
    }

    @GetMapping("/api/chat-rooms/recent")
    fun findRecent(): ResponseEntity<ChatRoomPageResponse> {
        return ResponseEntity.ok(chatRoomService.findRecent())
    }

    @GetMapping("/api/chat-rooms/join/{chatRoomId}")
    fun find(
        @PathVariable("chatRoomId") chatRoomId: String,
        @AuthenticationPrincipal authUser: AuthUser
    ): ResponseEntity<JoinResponse> {
        return ResponseEntity.ok(chatRoomService.join(chatRoomId, authUser.username))
    }

    @PostMapping("/api/chat-rooms/exit")
    fun exitChatRoom(@Valid @RequestBody request: ExitChatRoomRequest) {
        chatRoomService.exit(request.roomId, request.userId)
    }

    @PostMapping("/api/chat-rooms/participant")
    fun addParticipant(@Valid @RequestBody request: ParticipantRequest) {
        chatRoomService.addParticipant(request.roomId, request.nickname)
    }
}