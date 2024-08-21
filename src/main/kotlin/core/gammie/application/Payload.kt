package core.gammie.application

import java.io.Serializable

data class Payload(
    val token: String,
    val body: Any,
    val commend: Commend
) : Serializable
