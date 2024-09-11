package core.gammieapi.auth.application

import core.gammieapi.auth.domain.AuthUser
import core.gammieapi.auth.domain.JwtAuthentication
import core.gammieapi.auth.domain.Role
import org.springframework.stereotype.Service

@Service
class JwtAuthenticationProvider {
    fun provide(payload: Map<String, Any>): JwtAuthentication {
        val nickname = payload[NICKNAME_PAYLOAD] as String
        val authUser = AuthUser(nickname, Role.MEMBER)
        return JwtAuthentication(authUser, true)
    }

    companion object {
        private const val NICKNAME_PAYLOAD = "nickname"
    }
}
