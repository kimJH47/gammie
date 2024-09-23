package core.gammieapi.application

import jakarta.validation.constraints.NotBlank

data class ExitChatRoomRequest(
    @field:NotBlank(message = "roomId cannot be blank")
    val roomId: String,
    @field:NotBlank(message = "userId cannot be blank")
    val userId: String
)