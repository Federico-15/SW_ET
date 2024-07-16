package SW_ET.repository;

import SW_ET.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserId(String userId);
    Optional<User> findByUserNickName(String userNickName);
    boolean existsByUserId(String userId);
    boolean existsByUserNickName(String userNickName);

    Optional<User> findByUserKeyId(Long userKeyId);
}

