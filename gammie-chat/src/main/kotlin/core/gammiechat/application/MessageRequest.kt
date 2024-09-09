package core.gammiechat.application

data class MessageRequest(
    val roomId: String,
    val content: String,
    val userId: String,
)