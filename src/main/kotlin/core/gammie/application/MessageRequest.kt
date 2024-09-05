package core.gammie.application

data class MessageRequest(
    val roomId: String,
    val content: String,
    val userId: String,
)