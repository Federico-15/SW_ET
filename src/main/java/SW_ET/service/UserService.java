package SW_ET.service;

import SW_ET.config.JwtTokenProvider;
import SW_ET.dto.LoginDto;
import SW_ET.dto.UserDto;
import SW_ET.entity.User;
import SW_ET.exceptions.UserServiceException;
import SW_ET.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService{

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;


    public String registerUser(UserDto userDto) {
        if (userDto.getUserId().length() < 4) {
            throw new UserServiceException("User ID must be at least 4 characters long.");
        }
        if (userDto.getUserPassword().length() < 8) {
            throw new UserServiceException("Password must be at least 8 characters long.");
        }
        if (userRepository.existsByUserId(userDto.getUserId())) {
            logger.error("User ID '{}' already exists.", userDto.getUserId());
            throw new UserServiceException("User ID '" + userDto.getUserId() + "' already exists.");
        }
        if (!userDto.getUserPassword().equals(userDto.getConfirmPassword())) {
            logger.error("Passwords do not match for user '{}'.", userDto.getUserId());
            throw new UserServiceException("Passwords do not match.");
        }
        User newUser = userDto.toUser();
        newUser.setUserPassword(passwordEncoder.encode(userDto.getUserPassword()));
        userRepository.save(newUser);
        logger.info("User '{}' successfully registered.", newUser.getUserId());
        return "success";
    }

    public User getUserByUserId(String userId) {
        return userRepository.findByUserId(userId).orElse(null);
    }

    public boolean isUserIdExists(String userId) {
        return userRepository.existsByUserId(userId);
    }


    public User getUserByUserNickname(String userNickname) {
        return userRepository.findByUserNickName(userNickname).orElse(null);
    }

    public boolean isUserNickNameExists(String userNickName) {
        return userRepository.existsByUserNickName(userNickName);
    }

    public String login(LoginDto loginDto) {
        User user = userRepository.findByUserId(loginDto.getUserId()).orElse(null);

        // 로그: 사용자 검색 시도
        logger.debug("Attempting to find user with userId: {}", loginDto.getUserId());

        if (user != null) {
            // 로그: 사용자 발견
            logger.debug("User found: {}", loginDto.getUserId());

            if (passwordEncoder.matches(loginDto.getUserPassword(), user.getUserPassword())) {
                // 로그: 비밀번호 일치
                logger.debug("Password matches for user: {}", loginDto.getUserId());

                Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);

                String jwt = jwtTokenProvider.generateToken(authentication);
                // 로그: JWT 생성
                logger.debug("JWT generated for user: {}", loginDto.getUserId());

                return jwt;
            } else {
                // 로그: 비밀번호 불일치
                logger.error("Invalid credentials for user: {}. Password does not match.", loginDto.getUserId());
                throw new UserServiceException("Invalid credentials. Password does not match.");
            }
        } else {
            // 로그: 사용자 미발견
            logger.error("No user found with userId: {}", loginDto.getUserId());
            throw new UserServiceException("Invalid credentials. No such user.");
        }
    }

    public User getUserByUserKeyId(Long userKeyId) {
        return userRepository.findByUserKeyId(userKeyId).orElse(null);
    }
}