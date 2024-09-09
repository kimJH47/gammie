package core.gammiechat.application

enum class ResponseType(
    val description: String
) {
    CONNECTED("연결 성공"),
    RECEIVE_CHAT("채팅메시지 수신")
}