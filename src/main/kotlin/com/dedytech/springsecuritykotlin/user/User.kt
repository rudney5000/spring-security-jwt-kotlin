package com.dedytech.springsecuritykotlin.user

import jakarta.persistence.*
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Entity
@Table(name = "_user")
data class User(
    @Id
    @GeneratedValue
    var id: Int? = null,
    var firstname: String = "",
    var lastname: String = "",
    var email: String = "",
    var password: String = "",

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    var roles: List<Role> = listOf(Role.USER, Role.ADMIN)

) {
    fun toUserDetails(): UserDetails {
        val user = this@User
        return object:UserDetails {

            override fun getAuthorities() = user.roles.map { role ->
                SimpleGrantedAuthority(role.name)
            }.toList()

            override fun getPassword(): String {
                return password
            }

            override fun getUsername() = email

            override fun isAccountNonExpired() = true

            override fun isAccountNonLocked() = true

            override fun isCredentialsNonExpired() = true

            override fun isEnabled() = true

        }
    }

}