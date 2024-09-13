package core.gammiechat.application

data class ChatDto(
    val roomId: String,
    val userId: String,
    val nickname: String,
    val content : String,
)