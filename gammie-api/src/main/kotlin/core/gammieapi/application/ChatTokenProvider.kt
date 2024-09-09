package core.gammieapi.application

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.*

@Service
class ChatTokenProvider(
    @Value("\${jwt.secret-key}")
    private val secretKey: String,
) {


    fun provide(roomId: String, userId: String, expiredTime: Int): String {
        val payload = HashMap<String, Any>()
        payload["roomId"] = roomId
        payload["userId"] = userId

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
