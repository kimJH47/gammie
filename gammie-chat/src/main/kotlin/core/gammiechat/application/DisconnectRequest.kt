package core.gammiechat.application

data class DisconnectRequest(
    val roomId: String,
    val userId: String,
)
