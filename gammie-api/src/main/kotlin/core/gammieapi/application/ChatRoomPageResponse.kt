package core.gammieapi.application

data class ChatRoomPageResponse(
    val lastId: String,
    val chatRooms: List<ChatRoomResponse>,
)
