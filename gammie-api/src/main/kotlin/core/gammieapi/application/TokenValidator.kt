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
    private val jwtParser: JwtParser = Jwts.parser()

    fun validate(token: String) {
        jwtParser.setSigningKey(secretKey)
        kotlin.runCatching {
            jwtParser.parseClaimsJws(token)
        }.getOrElse { throw AuthException() }
    }
}