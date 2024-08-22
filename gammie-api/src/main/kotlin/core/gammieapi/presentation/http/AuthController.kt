package core.gammieapi.presentation.http

import core.gammieapi.application.AuthService
import core.gammieapi.application.SignupRequest
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthController(
    private val authService: AuthService
) {

    @PostMapping("/api/signup")
    fun signup(@RequestBody @Valid request: SignupRequest): ResponseEntity<String> {
        return ResponseEntity.ok(authService.signUp(request.nickname))
    }
}