package core.gammiechat.application

import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
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
}