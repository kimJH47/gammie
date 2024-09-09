package core.gammieapi.application

data class JoinResponse(
    val token: String,
    val roomId: String,
    val userId: String,
)
