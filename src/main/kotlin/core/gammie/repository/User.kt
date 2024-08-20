package core.gammie.repository

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "users")
class User(
    nickname: String
) : ULIDPrimaryKeyEntity() {

    @Column(unique = true, nullable = false)
    var nickname: String = nickname
        protected set
}