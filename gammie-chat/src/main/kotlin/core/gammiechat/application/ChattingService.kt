package core.gammiechat.application

import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class ChattingService {

    fun processMessage(request: MessageRequest): Mono<String> {
        return Mono.just(request.content)
    }
}
