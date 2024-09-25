package core.gammieapi.repository

import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.data.mongodb.repository.Update
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ChatRoomRepository : MongoRepository<ChatRoom, UUID> {

    @Query("{ 'id': { '\$gt': ?0 } }")
    fun findByIdGreaterThan(lastId: UUID, pageable: Pageable): List<ChatRoom>

    fun findAllBy(pageable: Pageable): List<ChatRoom>

    override fun existsById(id: UUID): Boolean

    @Update("{ '\$inc': { 'joinCount': -1 } }")
    fun findAndDecrementJoinCountById(id: UUID)

    @Update("{ '\$inc': { 'joinCount': 1 } }")
    fun findAndIncrementJoinCountById(id: UUID)

    @Query("{ 'name': { \$regex: ?0, \$options: 'i' } }")
    fun findByName(name: String): List<ChatRoom>
}
