package core.gammiechat.application

import core.gammiechat.exception.CustomSocketException
import core.gammiechat.exception.ErrorCode
import io.jsonwebtoken.JwtParser
import io.jsonwebtoken.Jwts
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class TokenValidator(
    @Value("\${jwt.secret-key}")
    private val secretKey: String,
) {
    private val jwtParser: JwtParser = Jwts.parser()

    fun validateAndGetPayload(token: String): Map<String, Any> {
        jwtParser.setSigningKey(secretKey)
        kotlin.runCatching {
            val parseClaimsJws = jwtParser.parseClaimsJws(token)
            return parseClaimsJws.body.toMap()
        }.getOrElse { throw CustomSocketException(ErrorCode.INVALID_TOKEN) }
    }
}