package SW_ET.entity;

import SW_ET.entity.types.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import jakarta.persistence.*;
import lombok.Data;
import java.util.Collection;
import java.util.Collections;
import java.time.LocalDate;

@Data
@Entity
@Table(name="Users")
public class User implements UserDetails {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "user_key_id", nullable = false, columnDefinition = "INT UNSIGNED")
        private Long userKeyId;

        @Column(name = "user_id", nullable = false, unique = true, length = 255)
        private String userId;

        @Column(name = "user_Nickname", nullable = false, unique = true, length = 255)
        private String userNickName;

        @Column(name = "user_password", nullable = false, length = 255)
        private String userPassword;

        @Column(name = "regist_date", nullable = false)
        private LocalDate registDate = LocalDate.now();

        @Enumerated(EnumType.STRING)
        @Column(nullable = true)
        private UserRole userRole;

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
                return userRole != null ? Collections.singletonList(new SimpleGrantedAuthority(userRole.name())) : Collections.emptyList();
        }

        @Override
        public String getPassword() {
                return userPassword;
        }

        @Override
        public String getUsername() {
                return userId;
        }

        @Override
        public boolean isAccountNonExpired() {
                return true;  // Consider your business logic here
        }

        @Override
        public boolean isAccountNonLocked() {
                return true;  // Consider your business logic here
        }

        @Override
        public boolean isCredentialsNonExpired() {
                return true;  // Consider your business logic here
        }

        @Override
        public boolean isEnabled() {
                return true;  // Consider your business logic here
        }
}
