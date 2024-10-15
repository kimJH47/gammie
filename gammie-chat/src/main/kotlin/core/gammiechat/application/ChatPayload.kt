package core.gammiechat.application

import core.gammiechat.exception.ChatException
import core.gammiechat.exception.ErrorCode

data class ChatPayload(
    val userId: String,
    val roomId: String
) {
    companion object {
        fun createWithPayload(payload: Map<String, Any>): ChatPayload {
            if (payload.containsKey("userId") && payload.containsKey("roomId")) {
                return ChatPayload(payload["userId"].toString(), payload["userId"].toString())
            }
            throw ChatException(ErrorCode.INVALID_TOKEN)
        }
    }
}