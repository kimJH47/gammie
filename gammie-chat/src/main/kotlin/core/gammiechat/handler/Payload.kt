package core.gammiechat.handler

import com.fasterxml.jackson.annotation.JsonCreator
import java.io.Serializable

data class Payload @JsonCreator constructor(
    val body: Any = "",
    val command: Command
): Serializable