package core.gammieapi.application

enum class ErrorCode(
    val message: String
) {
    CHAT_ROOM_NOT_FOUND("채팅방이 존재하지 않습니다."),
    USER_NOT_FOUND("유저가 존재하지 않습니다.")
}