package core.gammieapi.repository

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import java.util.*

@Entity
@Table(name = "user_participant")
class UserParticipant(
    userId: String,
    roomId: UUID
) : ULIDPrimaryKeyEntity() {

    @Column
    var userId: String = userId
        protected set

    @Column
    var roomId: UUID = roomId
        protected set
}