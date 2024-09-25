package core.gammiechat.application.dto

data class ChatRoomResponse(
    val id: String,
    val name: String,
    val description: String,
    val joinCount: Int,
    val imageUrl: String
)
