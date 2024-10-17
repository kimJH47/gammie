package core.gammiechat.exception

enum class ErrorCode(
    val message: String
) {
    COMMAND_NOT_FOUND("일치하는 커멘드가 존재하지 않습니다."),
    INVALID_TOKEN("토큰이 유효하지 않습니다."),
    BAD_REQUEST_CHAT_API("API 서버 응답이 유효하지 않습니다."),
    INTERNAL_API_SERVER_ERROR("API 서버에 문제가 발생 했습니다."),
    FAIL_SEND_MESSAGE("레디스 메시지 발행에 문제가 발생 했습니다.") {
    }
}
