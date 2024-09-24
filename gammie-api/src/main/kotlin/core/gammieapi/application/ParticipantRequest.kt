package core.gammieapi.application

import jakarta.validation.constraints.NotBlank

data class ParticipantRequest(
    @field:NotBlank(message = "roomId cannot be blank")
    val roomId: String,
    @field:NotBlank(message = "nickname cannot be blank")
    val nickname: String
)
