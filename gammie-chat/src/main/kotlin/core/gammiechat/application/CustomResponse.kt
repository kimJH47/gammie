package core.gammiechat.application

import java.io.Serializable


data class CustomResponse(
    val type: ResponseType,
    val body: Any? = null
): Serializable
