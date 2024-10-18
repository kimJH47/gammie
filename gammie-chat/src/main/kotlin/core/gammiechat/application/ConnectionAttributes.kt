package core.gammiechat.application

import io.netty.util.AttributeKey


class ConnectionAttributes {

    companion object {
        val CHAT_PAYLOAD_KEY: AttributeKey<ChatPayload> = AttributeKey.valueOf("chatPayload")
    }

}