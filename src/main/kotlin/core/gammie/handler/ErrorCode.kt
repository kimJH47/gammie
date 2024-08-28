package core.gammie.handler

enum class ErrorCode(
    val message: String
) {
    COMMAND_NOT_FOUND("일치하는 커멘드가 존재하지 않습니다."),
    INVALID_TOKEN("토큰이 유효하지 않습니다."),
    CHAT_ROOM_NOT_FOUND("채팅방이 존재하지 않습니다.")
}
