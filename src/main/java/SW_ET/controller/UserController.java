package SW_ET.controller;

import SW_ET.config.JwtTokenProvider;
import SW_ET.dto.LoginDto;
import SW_ET.dto.UserDto;
import SW_ET.exceptions.UserServiceException;
import SW_ET.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collections;
import java.util.Optional;

@Controller
@RequestMapping("/users")
@Tag(name = "User Management", description = "Operations pertaining to user management in the application")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    private boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getPrincipal());
    }


    @Operation(summary = "Show Registration Form")
    @GetMapping("/register")
    public String showRegistrationForm(Model model, HttpSession session) {
        if (isAuthenticated()) {
            log.info("User already authenticated. Redirecting to home.");
            return "redirect:/users/home";  // Redirect to home if already logged in
        }
        model.addAttribute("user", new UserDto());
        log.info("Displaying registration form.");
        return "users/register";
    }

    @Operation(summary = "Register a new User")
    @PostMapping(value = "/register", consumes = "application/json")
    public ResponseEntity<?> register(@Valid @RequestBody UserDto userDto, BindingResult result) {
        if (isAuthenticated()) {
            log.warn("User is already authenticated. Cannot register a new user.");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Logout required");  // Block registration if already logged in
        }
        if (result.hasErrors()) {
            log.error("Registration form contains errors: {}", result.getAllErrors());
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }
        try {
            userService.registerUser(userDto);
            log.info("User registered successfully.");
            //return ResponseEntity.status(HttpStatus.SEE_OTHER).location(URI.create("/users/login_proc")).build();
            return ResponseEntity.status(HttpStatus.OK).body(Collections.singletonMap("resultCode", "0000"));

        } catch (Exception e) {
            log.error("User registration failed: {}", e.getMessage());
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", e.getMessage()));
        }
    }

    @Operation(summary = "Show Login Form")
    @GetMapping("/login_proc")
    public String showLoginForm(HttpSession session) {
        if (isAuthenticated()) {
            log.info("User already authenticated. Redirecting to home.");
            return "redirect:/users/home";  // Redirect to home if already logged in
        }
        log.info("Displaying login form.");
        return "users/login_proc";
    }

    @Operation(summary = "Login a user", description = "Log in a user and return a JWT")
    @PostMapping("/login_proc")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto, HttpServletRequest request) {
        if (isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Logout required");  // Block login if already logged in
        }
        try {
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(loginDto.getUserId(), loginDto.getUserPassword());
            Authentication authentication = authenticationManager.authenticate(authToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = jwtTokenProvider.generateToken(authentication);

            if(Optional.ofNullable(request.getSession()).isPresent()) {
                log.error("[login_proc] 로그인시 session 있음");
            }
            request.getSession().setAttribute("token", jwt);
            log.error("[proc] {}", request.getSession().getAttribute("token"));
            return ResponseEntity.ok(Collections.singletonMap("jwt", jwt));
        } catch (UserServiceException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }


    @Operation(summary = "Show Home Page with conditional content")
    @GetMapping("/home")
    public String showHomePage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAuthenticated = authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getPrincipal());
        model.addAttribute("isAuthenticated", isAuthenticated);
        return "/users/home";  // 로그인 한 유저와 로그인 안한 유저가 같은 페이지를 공유함.. 클라이언트에서 권한에 따라 보여주는 페이지 나눠주면 됨.
    }


    @Operation(summary = "Logout a User")
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpSession session) {
        session.invalidate();
        String token = jwtTokenProvider.resolveToken(request);
        if (token != null) {
            jwtTokenProvider.invalidateToken(token);
            log.debug("Token on logout: {}", token);
        } else {
            log.debug("No token found on logout request.");
        }
        return "redirect:/users/login";
    }

    @Operation(summary = "Check if UserId exists")
    @GetMapping("/check-userId")
    public ResponseEntity<Boolean> checkUserIdExists(@RequestParam("userId") String userId) {
        boolean exists = userService.isUserIdExists(userId);
        return ResponseEntity.ok(exists);
    }

    @Operation(summary = "Check if UserNickname exists")
    @GetMapping("/check-nickname")
    public ResponseEntity<Boolean> checkUserNicknameExists(@RequestParam("userNickName") String userNickName) {
        boolean exists = userService.isUserNickNameExists(userNickName);
        return ResponseEntity.ok(exists);
    }
}