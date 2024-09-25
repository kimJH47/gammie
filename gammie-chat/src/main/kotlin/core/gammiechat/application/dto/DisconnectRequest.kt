package core.gammiechat.application.dto

data class DisconnectRequest(
    val roomId: String,
    val userId: String,
)
