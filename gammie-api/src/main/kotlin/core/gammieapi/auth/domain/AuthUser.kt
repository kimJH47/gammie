package core.gammieapi.auth.domain

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class AuthUser(
    private val username: String,
    private val role: Role,
) : UserDetails {

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableListOf(SimpleGrantedAuthority(role.toString()))
    }

    override fun getPassword(): String {
        return ""
    }

    override fun getUsername(): String {
        return username
    }
}