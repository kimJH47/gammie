package core.gammieapi.repository

import com.github.f4b6a3.ulid.UlidCreator
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document(collection = "chatrooms")
class ChatRoom(
    private val id: UUID = UlidCreator.getMonotonicUlid().toUuid(),
    private val name: String,
    private val description: String,
    private val joinCount: Int
) {

}