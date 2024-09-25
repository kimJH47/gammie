package core.gammiechat.application.dto

import core.gammiechat.application.ResponseType
import java.io.Serializable


data class CustomResponse(
    val type: ResponseType,
    val body: Any? = null
): Serializable
