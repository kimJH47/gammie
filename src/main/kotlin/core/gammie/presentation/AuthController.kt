package core.gammie.presentation

import core.gammie.application.AuthService
import core.gammie.application.SignupRequest
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthController(
    private val authService: AuthService
) {

    @PostMapping("/api/signup")
    fun signup(@RequestBody @Valid request: SignupRequest) {
        authService.signUp(request.nickname)
    }
}