package SW_ET.controller;

import SW_ET.config.JwtTokenProvider;
import SW_ET.dto.ReviewDto;
import SW_ET.dto.TravelDestinationDto;
import SW_ET.entity.User;
import SW_ET.service.TravelDestinationReviewService;
import SW_ET.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api")
@Slf4j
public class UserProfileController {

    @Autowired
    private TravelDestinationReviewService travelDestinationReviewService;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;


    /*@GetMapping("/my-page")
    @ResponseBody // 이 어노테이션은 응답을 JSON 형식으로 직접 반환하도록 지시합니다.
    public ResponseEntity<Map<String, Object>> getMyPageData(Principal principal) {
        User user = userService.getUserByUserNickname(principal.getName());
        List<TravelDestinationDto> favoriteDestinations = travelDestinationReviewService.getFavoritesByUser(user);
        List<ReviewDto> userReviews = travelDestinationReviewService.getReviewsByUser(user);

        Map<String, Object> response = new HashMap<>();
        response.put("favoriteDestinations", favoriteDestinations);
        response.put("userReviews", userReviews);

        return ResponseEntity.ok(response);
    }*/

    @GetMapping("/my-page")
    public ResponseEntity<Map<String, Object>> getMyPageData(@RequestHeader("Authorization") String token) {
        if (token == null || !token.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        String jwtToken = token.substring(7);
        if (!jwtTokenProvider.validateToken(jwtToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        Long userKeyId = jwtTokenProvider.getUserKeyIdFromToken(jwtToken);
        User user = userService.getUserByUserKeyId(userKeyId);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        List<TravelDestinationDto> favoriteDestinations = travelDestinationReviewService.getFavoritesByUser(user);
        List<ReviewDto> userReviews = travelDestinationReviewService.getReviewsByUser(user);

        Map<String, Object> response = new HashMap<>();
        response.put("favoriteDestinations", favoriteDestinations);
        response.put("userReviews", userReviews);

        return ResponseEntity.ok(response);
    }
}