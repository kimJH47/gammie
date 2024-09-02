package core.gammieapi.presentation.http

import core.gammieapi.application.ChatRoomPageResponse
import core.gammieapi.application.ChatRoomResponse
import core.gammieapi.application.ChatRoomService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

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
    fun findRecent() : ResponseEntity<ChatRoomPageResponse> {
        return ResponseEntity.ok(chatRoomService.findRecent())
    }
}