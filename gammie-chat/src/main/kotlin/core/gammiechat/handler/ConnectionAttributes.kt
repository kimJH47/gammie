package core.gammiechat.handler

import io.netty.util.AttributeKey


class ConnectionAttributes {

    companion object {
        val ROOM_ID_KEY: AttributeKey<String> = AttributeKey.valueOf("roomId")
        val USER_ID_KEY: AttributeKey<String> = AttributeKey.valueOf("userId")
    }

}