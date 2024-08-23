package core.gammieapi.application

import jakarta.validation.constraints.NotBlank

data class LoginRequest(
    @field:NotBlank(message = "Please provide a nickname")
    val nickname: String,
) {

}
