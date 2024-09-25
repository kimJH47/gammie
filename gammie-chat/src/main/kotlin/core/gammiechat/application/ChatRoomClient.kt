package core.gammiechat.application

import core.gammiechat.application.dto.ExitChatRoomRequest
import core.gammiechat.exception.ChatException
import core.gammiechat.exception.ErrorCode
import org.springframework.http.HttpStatusCode
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono
import reactor.core.publisher.Mono

@Component
class ChatRoomClient(
    private val webClient: WebClient
) {

    fun exitChatRoom(roomId: String, userId: String): Mono<Unit> {
        return webClient.post()
            .uri("api/chat-rooms/exit")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(ExitChatRoomRequest(roomId, userId))
            .retrieve()
            .onStatus(HttpStatusCode::is4xxClientError) {
                Mono.error(ChatException(ErrorCode.BAD_REQUEST_CHAT_API))
            }
            .bodyToMono<Unit>()
    }

    fun addParticipant(roomId: String, userId: String): Mono<Unit> {
        return webClient.post()
            .uri("api/chat-rooms/participant")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(ParticipantRequest(roomId, userId))
            .retrieve()
            .onStatus(HttpStatusCode::is4xxClientError) {
                Mono.error(ChatException(ErrorCode.BAD_REQUEST_CHAT_API))
            }
            .bodyToMono<Unit>()

    }
}
