package core.gammie.application

data class ChatRoomResponse(
    val id: String,
    val name: String,
    val description: String,
    val joinCount: Int,
    val imageUrl: String
)
