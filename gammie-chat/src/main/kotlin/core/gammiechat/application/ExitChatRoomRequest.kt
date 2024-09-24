package core.gammiechat.application

data class ExitChatRoomRequest(
    val roomId: String,
    val userId: String
)