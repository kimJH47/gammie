package core.gammieapi.presentation.http

import core.gammieapi.application.ChatRoomResponse
import core.gammieapi.application.ChatRoomService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class ChatRoomController(
    private val chatRoomService: ChatRoomService
) {

    @GetMapping("/api/{id}")
    fun getChatRoom(@PathVariable("id") chatRoomId: Long): ResponseEntity<ChatRoomResponse> {
        return ResponseEntity.ok(chatRoomService.findOne(chatRoomId))
    }
}