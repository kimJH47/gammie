package core.gammie.application

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.NotBlank

data class SignupRequest(
    @field:NotBlank(message = "Name cannot be empty")
    @field:Max(value = 25L, message = "max name cannot be less than 25")
    val nickname : String,
)

