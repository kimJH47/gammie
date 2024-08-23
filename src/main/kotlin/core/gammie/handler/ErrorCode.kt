package core.gammie.handler

enum class ErrorCode(
    val message: String
) {
    COMMAND_NOT_FOUND("일치하는 커멘드가 존재하지 않습니다.")
}
