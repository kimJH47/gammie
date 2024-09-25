package core.gammiechat.application

import io.netty.util.AttributeKey
import reactor.core.Disposable


class ConnectionAttributes {

    companion object {
        val ROOM_ID_KEY: AttributeKey<String> = AttributeKey.valueOf("roomId")
        val USER_ID_KEY: AttributeKey<String> = AttributeKey.valueOf("userId")
        val SUBSCRIPTION_KEY: AttributeKey<Disposable> = AttributeKey.valueOf("subscription")
    }

}