package core.gammiechat.application.dto

data class ExitChatRoomRequest(
    val roomId: String,
    val userId: String
)