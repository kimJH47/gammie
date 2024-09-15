package core.gammiechat.application

import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class ChattingService {

    fun processChatDto(request: MessageRequest): Mono<ChatDto> {
        return Mono.just(ChatDto(request.roomId, request.userId, request.userId, request.content))
    }
}
