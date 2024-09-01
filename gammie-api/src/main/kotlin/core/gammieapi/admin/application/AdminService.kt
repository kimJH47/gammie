package core.gammieapi.admin.application

import core.gammieapi.repository.ChatRoom
import core.gammieapi.repository.ChatRoomReactiveRepository
import org.springframework.stereotype.Service

@Service
class AdminService(
    private val reactiveRepository: ChatRoomReactiveRepository,
    private val igdbClient: IGDBClient
) {
    fun addGames() {
        igdbClient.add()
            .flatMap {
                val chatRoom = ChatRoom(
                    name = it.name,
                    joinCount = 0,
                    description = "",
                    imageUrl = "https://images.igdb.com/igdb/image/upload/t_cover_big/${it.cover.imageId}.jpg",
                    genres = it.genres
                )
                reactiveRepository.save(chatRoom)
            }
            .doOnError {
                it.printStackTrace()
            }
            .subscribe()
    }
}
