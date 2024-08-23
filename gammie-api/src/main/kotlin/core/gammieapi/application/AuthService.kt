package core.gammieapi.application

import core.gammieapi.repository.User
import core.gammieapi.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val tokenProvider: TokenProvider
) {
    @Transactional
    fun signUp(nickname: String): String {
        validateDuplicate(nickname)
        return userRepository.save(User(nickname)).id.toString()
    }

    private fun validateDuplicate(nickname: String) {
        if (userRepository.existsByNickname(nickname)) {
            throw IllegalArgumentException("User with nickname $nickname already exists.")
        }
    }

    @Transactional(readOnly = true)
    fun login(nickname: String): LoginResponse {
        if (userRepository.existsByNickname(nickname)) {
            return LoginResponse(tokenProvider.provide(nickname, EXPIRED_TIME))
        }
        throw IllegalArgumentException("User with nickname $nickname does not exist.")
    }

    companion object {
        private const val EXPIRED_TIME = 1000 * 60 * 30
    }
}
