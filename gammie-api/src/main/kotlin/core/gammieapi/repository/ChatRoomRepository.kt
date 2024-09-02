package core.gammieapi.repository

import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ChatRoomRepository : MongoRepository<ChatRoom, UUID> {

    @Query("{ 'id': { '\$gt': ?0 } }")
    fun findByIdGreaterThan(lastId: UUID, pageable: Pageable): List<ChatRoom>

    fun findAllBy(pageable: Pageable): List<ChatRoom>
}
