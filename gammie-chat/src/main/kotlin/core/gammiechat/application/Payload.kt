package core.gammiechat.application

import com.fasterxml.jackson.annotation.JsonCreator
import java.io.Serializable

data class Payload @JsonCreator constructor(
    val token: String,
    val body: Any = "",
    val command: Command
): Serializable