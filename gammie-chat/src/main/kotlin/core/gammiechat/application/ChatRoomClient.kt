package core.gammiechat.application

import core.gammiechat.application.dto.ExitChatRoomRequest
import core.gammiechat.application.dto.ParticipantRequest
import core.gammiechat.exception.ChatException
import core.gammiechat.exception.ErrorCode
import core.gammiechat.logger
import org.springframework.http.HttpStatusCode
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono
import reactor.core.publisher.Mono
import reactor.util.retry.Retry
import java.time.Duration

@Component
class ChatRoomClient(
    private val webClient: WebClient
) {
    private val log = logger()

    fun exitChatRoom(roomId: String, userId: String): Mono<Unit> {
        return webClient.post()
            .uri("/api/chat-rooms/exit")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(ExitChatRoomRequest(roomId, userId))
            .retrieve()
            .onStatus(HttpStatusCode::is4xxClientError) {
                Mono.error(ChatException(ErrorCode.BAD_REQUEST_CHAT_API))
            }
            .bodyToMono<Unit>()
    }

    //예외전파 x
    fun addParticipant(roomId: String, userId: String): Mono<Unit> {
        return webClient.post()
            .uri("/api/chat-rooms/participant")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(ParticipantRequest(roomId, userId))
            .retrieve()
            .onStatus(HttpStatusCode::is5xxServerError) {
                Mono.error(ChatException(ErrorCode.INTERNAL_API_SERVER_ERROR))
            }
            .onStatus(HttpStatusCode::is4xxClientError) {
                Mono.error(ChatException(ErrorCode.BAD_REQUEST_CHAT_API))
            }.bodyToMono<Unit>()
            .retryWhen(
                Retry.fixedDelay(2, Duration.ofSeconds(5))
                    .filter { it.message.equals(ErrorCode.INTERNAL_API_SERVER_ERROR.message) }
            )
            .onErrorResume {
                log.warn("failed add participant count, message: ${it.message}")
                Mono.just(Unit)
            }
    }
}
