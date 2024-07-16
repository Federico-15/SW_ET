package SW_ET.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenVerificationController {

    @PostMapping("/verify-token")
    public ResponseEntity<?> verifyToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return ResponseEntity.ok().body("{\"message\": \"Token is valid\"}");  // JSON 형태로 응답 수정
        } else {
            return ResponseEntity.status(403).body("{\"message\": \"Access Denied: Invalid token\"}");  // 여기도 마찬가지로 수정
        }
    }
}