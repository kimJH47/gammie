package core.gammieapi.repository

import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ChatRoomReactiveRepository : ReactiveMongoRepository<ChatRoom, UUID>