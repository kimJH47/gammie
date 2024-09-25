package core.gammiechat.application.dto

data class MessageRequest(
    val roomId: String,
    val content: String,
    val userId: String,
)