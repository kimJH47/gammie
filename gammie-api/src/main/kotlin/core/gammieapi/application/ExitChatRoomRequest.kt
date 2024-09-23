package core.gammieapi.application

data class ExitChatRoomRequest(
    val roomId: String,
    val userId: String
)