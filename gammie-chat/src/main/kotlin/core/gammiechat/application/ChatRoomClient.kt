package core.gammiechat.application

import core.gammiechat.handler.ChatException
import core.gammiechat.handler.ErrorCode
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

    fun asyncFindChatRoomById(id: String): Mono<ChatRoomResponse> {
        return webClient.get()
            .uri("api/chat-rooms/{id}", id)
            .retrieve()
            .bodyToMono(ChatRoomResponse::class.java)
    }

    fun exitChatRoom(roomId: String, userId: String): Mono<Unit> {
        return webClient.post()
            .uri("api/chat-rooms/exit")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(ExitChatRoomRequest(roomId, userId))
            .retrieve()
            .onStatus(HttpStatusCode::is4xxClientError) {
                Mono.error(ChatException(ErrorCode.BAD_REQEUST_CHAT_API))
            }
            .bodyToMono<Unit>()
    }
}
