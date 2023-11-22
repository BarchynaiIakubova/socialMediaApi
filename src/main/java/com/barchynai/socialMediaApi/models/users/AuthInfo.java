package com.barchynai.socialMediaApi.models.users;

import com.barchynai.socialMediaApi.enums.Role;
import javax.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;

@Entity
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "auth_info")
public class AuthInfo implements UserDetails {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "auth_info_gen")

    @SequenceGenerator(
            name = "auth_info_gen",
            sequenceName = "auth_info_seq",
            allocationSize = 1)
    private Long id;

    private String username;
    private String email;
    private String password;
    private LocalDate dateOfCreate;

    @Enumerated(EnumType.STRING)
    private Role role;

    public AuthInfo(String username, String email, String password, Role role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(role);
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
