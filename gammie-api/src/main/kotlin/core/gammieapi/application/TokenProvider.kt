package core.gammieapi.application

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*

@Component
class TokenProvider(
    @Value("\${jwt.secret-key}")
    private val secretKey: String,
) {
    fun provide(nickname: String, expiredTime: Int): String {
        val payload = HashMap<String, Any>()
        payload["nickname"] = nickname

        return Jwts.builder()
            .setHeaderParam("typ", "JWT")
            .setHeaderParam("alg", SIGNATURE_ALGORITHM)
            .setClaims(payload)
            .setExpiration(createExpireTime(expiredTime))
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact()
    }

    private fun createExpireTime(expireTime: Int): Date {
        return Date(System.currentTimeMillis() + expireTime)
    }

    companion object {
        private val SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256
    }
}