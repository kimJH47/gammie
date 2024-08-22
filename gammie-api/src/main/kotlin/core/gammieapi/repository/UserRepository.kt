package core.gammieapi.repository

import org.springframework.data.repository.CrudRepository

interface UserRepository : CrudRepository<User, Long> {
    fun existsByNickname(nickname: String): Boolean
}