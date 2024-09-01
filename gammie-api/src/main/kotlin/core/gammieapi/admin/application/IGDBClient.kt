package core.gammieapi.admin.application

import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux

@Component
class IGDBClient(
    private val webClient: WebClient,
    @Value("\${twitch.client-id}")
    private val clientId: String,
    @Value("\${twitch.access-token}")
    private val accessToken: String
) {

    fun add(): Flux<IgdbGameResponse> {
        return webClient.post()
            .uri("https://api.igdb.com/v4/games")
            .headers {
                it.setBearerAuth(accessToken)
                it.add("Client-ID", clientId)
            }
            .contentType(MediaType.TEXT_PLAIN)
            .bodyValue("fields name, cover.image_id, rating, rating_count; where multiplayer_modes.onlinemax > 1 &rating_count > 10;limit 10;")
            .retrieve()
            .bodyToFlux(IgdbGameResponse::class.java)
    }
}