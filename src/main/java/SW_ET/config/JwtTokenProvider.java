package SW_ET.config;

import SW_ET.repository.UserRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.security.core.GrantedAuthority;

import jakarta.annotation.PostConstruct;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.SecretKey;

@Component
public class JwtTokenProvider {

    private static final Logger log = LoggerFactory.getLogger(JwtTokenProvider.class);

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserRepository userRepository;

    @Value("${security.jwt.token.secret-key:secret-key}")
    private String secretKeyString;

    @Value("${jwt.expiration}")
    private long jwtExpiration;

    private SecretKey secretKey;
    private Set<String> tokenBlacklist = ConcurrentHashMap.newKeySet();

    @PostConstruct
    protected void init() {
        if (secretKeyString.length() < 64) { // 64 바이트는 512 비트
            this.secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
            log.warn("Provided secret key is too short. A secure key has been generated.");
        } else {
            this.secretKey = Keys.hmacShaKeyFor(secretKeyString.getBytes());
        }
        log.info("JWT Secret key initialized.");
    }

    public String generateToken(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", userDetails.getUsername());
        claims.put("roles", userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()));
        claims.put("userKeyId", getUserKeyIdFromDatabase(userDetails.getUsername())); // 'userKeyId' 추가

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
    }

    public boolean validateToken(String token) {
        if (tokenBlacklist.contains(token)) {
            log.error("Token is blacklisted: {}", token);
            return false;
        }
        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            log.debug("Token is valid.");
            boolean expired = isTokenExpired(token);
            if (!expired) {
                log.info("Token is active and not expired.");
            } else {
                log.info("Token is expired.");
            }
            return !expired;
        } catch (ExpiredJwtException e) {
            log.error("Token has expired: {}", e.getMessage());
            return false;
        } catch (JwtException e) {
            log.error("Invalid token: {}", e.getMessage());
            return false;
        } catch (Exception e) {
            log.error("Error validating token: {}", e.getMessage());
            return false;
        }
    }

    public Authentication getAuthentication(String token) {
        Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
        String username = claims.getBody().getSubject();
        List<SimpleGrantedAuthority> authorities = ((List<?>) claims.getBody().get("roles")).stream()
                .map(authority -> new SimpleGrantedAuthority("ROLE_" + authority))
                .collect(Collectors.toList());

        log.info("Extracted roles from token for user {}: {}", username, authorities);

        return new UsernamePasswordAuthenticationToken(username, null, authorities);
    }

    public String getUsername(String token) {
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().getSubject();
    }

    private boolean isTokenExpired(String token) {
        Date expiration = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().getExpiration();
        boolean expired = expiration.before(new Date());
        if (expired) {
            log.info("Token has expired.");
        }
        return expired;
    }

    public String resolveToken(HttpServletRequest req) {
        Enumeration<String> headers = req.getHeaderNames();
        while (headers.hasMoreElements()) {
            String header = headers.nextElement();
            log.debug("Header {}: {}", header, req.getHeader(header));
        }
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public void invalidateToken(String token) {
        tokenBlacklist.add(token);
        log.info("Token invalidated: {}", token);
    }

    // JwtTokenProvider에서 사용자 ID 추출
    public Long getUserKeyIdFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

        log.debug("Claims: {}", claims);

        // 클레임이 존재하는지 확인
        if (claims.get("userKeyId") == null) {
            log.error("Claim 'userKeyId' is missing in the token.");
            throw new NullPointerException("Claim 'userKeyId' is missing in the token.");
        }

        return Long.valueOf(claims.get("userKeyId").toString());
    }

    private Long getUserKeyIdFromDatabase(String username) {
        return userRepository.findByUserId(username) // userId 사용
                .orElseThrow(() -> new UsernameNotFoundException("User not found with userId: " + username))
                .getUserKeyId();
    }
}
