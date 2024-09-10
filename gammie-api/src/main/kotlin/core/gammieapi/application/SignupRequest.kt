package core.gammieapi.application

import jakarta.validation.constraints.Size

data class SignupRequest(
    @field:Size(max = 25, min = 1, message = "max name cannot be less than 25")
    val nickname: String,
)

