package core.gammiechat.application.dto

import com.fasterxml.jackson.annotation.JsonCreator
import java.io.Serializable

data class ChatDto @JsonCreator constructor(
    val roomId: String = "",
    val userId: String = "",
    val nickname: String = "",
    val content: String = "",
) : Serializable