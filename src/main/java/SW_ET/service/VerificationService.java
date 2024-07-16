package SW_ET.service;/*
package SW_ET.service;

import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class VerificationService {

    private final Map<String, String> verificationTokens = new HashMap<>();
    private final Random random = new Random();

    // 인증번호를 생성하고 저장하는 메소드
    public String createVerificationToken(String userEmail) {
        String token = String.format("%06d", random.nextInt(999999));
        verificationTokens.put(userEmail, token);
        // 토큰 유효시간 설정 예: 10분
        // 이 예제에서는 토큰 유효시간 관리가 단순화되어 있음. 실제 구현시에는 별도의 타이머 또는 스케줄러를 사용할 수 있음.
        return token;
    }

    // 사용자가 제출한 인증번호를 검증하는 메소드
    public boolean verifyToken(String userEmail, String token) {
        String correctToken = verificationTokens.get(userEmail);
        return correctToken != null && correctToken.equals(token);
    }

    // 인증 후 토큰 삭제
    public void clearToken(String userEmail) {
        verificationTokens.remove(userEmail);
    }
}
*/
