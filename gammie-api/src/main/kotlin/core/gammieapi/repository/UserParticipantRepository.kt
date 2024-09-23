package core.gammieapi.repository

import org.springframework.data.repository.CrudRepository
import java.util.*

interface UserParticipantRepository : CrudRepository<UserParticipant, UUID> {

    fun deleteByUserIdAndRoomId(userId: String, roomId: UUID)
}