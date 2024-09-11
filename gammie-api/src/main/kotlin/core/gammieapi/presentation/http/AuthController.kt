package core.gammieapi.presentation.http

import core.gammieapi.application.AuthService
import core.gammieapi.application.LoginRequest
import core.gammieapi.application.LoginResponse
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
    fun signup(@RequestBody @Valid request: SignupRequest): ResponseEntity<Any> {
        return ResponseEntity.ok(mapOf("id" to authService.signUp(request.nickname)))
    }

    @PostMapping("/api/login")
    fun login(@RequestBody @Valid request: LoginRequest): ResponseEntity<LoginResponse> {
        return ResponseEntity.ok(authService.login(request.nickname))
    }
}