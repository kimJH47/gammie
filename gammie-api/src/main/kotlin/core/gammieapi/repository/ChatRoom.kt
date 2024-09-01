package core.gammieapi.repository

import com.github.f4b6a3.ulid.UlidCreator
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document(collection = "chatrooms")
open class ChatRoom(
    id: UUID = UlidCreator.getMonotonicUlid().toUuid(),
    name: String,
    description: String,
    joinCount: Int,
    imageUrl: String,
    genres: List<Int>,
) {
    @Id
    var id: UUID = id
        protected set
    var name: String = name
        protected set
    var description: String = description
        protected set
    var joinCount: Int = joinCount
        protected set
    var imageUrl: String = imageUrl
        protected set
    var genres: List<Int> = genres
        protected set
}
