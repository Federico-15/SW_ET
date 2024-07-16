/*
package SW_ET.config;

import SW_ET.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class UserDetailsImpl implements UserDetails {

    private Long userKeyId;
    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(User user) {
        this.userKeyId = user.getUserKeyId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.authorities = user.getAuthorities();
    }

    public Long getUserKeyId() {
        return userKeyId;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Adjust based on your business logic
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Adjust based on your business logic
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Adjust based on your business logic
    }

    @Override
    public boolean isEnabled() {
        return true; // Adjust based on your business logic
    }
}*/
