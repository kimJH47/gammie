package core.gammieapi.application

import io.jsonwebtoken.JwtParser
import io.jsonwebtoken.Jwts
import jakarta.security.auth.message.AuthException
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class TokenValidator(
    @Value("\${jwt.secret-key}")
    private val secretKey: String
) {

    private val jwtParser: JwtParser by lazy {
        Jwts.parser().setSigningKey(secretKey)
    }

    fun validateAndGetPayload(token: String): Map<String, Any> {
        kotlin.runCatching {
            return jwtParser.parseClaimsJws(token)
                .body
                .toMap()
        }.getOrElse { throw AuthException() }
    }
}