package core.gammie.application

import core.gammie.handler.CustomSocketException
import core.gammie.handler.ErrorCode
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

    fun validate(token: String) {
        jwtParser.setSigningKey(secretKey)
        kotlin.runCatching {
            jwtParser.parseClaimsJws(token)
        }.getOrElse { throw CustomSocketException(ErrorCode.INVALID_TOKEN) }
    }
}