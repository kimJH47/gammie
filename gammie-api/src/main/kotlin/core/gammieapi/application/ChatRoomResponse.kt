package core.gammieapi.application

data class ChatRoomResponse(
    val id: String,
    val name: String,
    val description: String,
    val joinCount: Int,
    val imageUrl : String,
    val genres : List<Int>
)
