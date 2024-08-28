package core.gammieapi.repository

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ChatRoomRepository : MongoRepository<ChatRoom, UUID>