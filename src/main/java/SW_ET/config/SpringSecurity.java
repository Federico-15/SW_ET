package SW_ET.config;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity(debug = true)  // Consider turning off debug in production
public class SpringSecurity {

    @Autowired
    private final JwtTokenProvider jwtTokenProvider;

    public SpringSecurity(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    /*@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors().and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**", "/v3/api-docs", "/users/check-userId", "/users/check-nickname").permitAll()
                .requestMatchers("/users/register", "/users/login_proc", "/users/home", "/verify-token", "/resources/**", "/images/**", "/css/**", "/js/**", "/favicon.ico","/static/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/reviews/{id}","/error").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/reviews/region/**", "/api/reviews/{reviewId}/likes/counts","/api/sub-regions/**","/api/regions/**","/api/reviews/detail/**").permitAll()  // 시/도 지역에 대한 리뷰보기 다 허용

                .requestMatchers(HttpMethod.GET, "/api/reviews/{reviewId}/comments").permitAll()  // 댓글 읽기는 모두 허용
                .requestMatchers(HttpMethod.POST, "/api/reviews/{reviewId}/comments").hasAuthority("ROLE_USER")  // 댓글 생성은 로그인한 사용자만
                .requestMatchers(HttpMethod.PUT, "/api/reviews/{reviewId}/comments/{commentId}").hasAuthority("ROLE_USER")  // 댓글 수정
                .requestMatchers(HttpMethod.DELETE, "/api/reviews/{reviewId}/comments/{commentId}").hasAuthority("ROLE_USER")  // 댓글 삭제

                .requestMatchers(HttpMethod.GET, "/api/reviews/register","/edit-review/**", "/users/myPage").hasAuthority("ROLE_USER")
                .requestMatchers(HttpMethod.POST, "/api/reviews/register", "/api/reviews/{reviewId}/likes/**","/edit-review/**").hasAuthority("ROLE_USER")
                .requestMatchers(HttpMethod.PUT, "/api/reviews/{id}").hasAuthority("ROLE_USER")
                .requestMatchers(HttpMethod.DELETE, "/api/reviews/{id}").hasAuthority("ROLE_USER")
                .requestMatchers(HttpMethod.GET,"/api/my-page").hasAuthority("ROLE_USER")
                .requestMatchers("/users/logout").authenticated()


                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new JwtTokenFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/users/logout", "POST"))
                .logoutSuccessHandler((request, response, authentication) -> {
                    response.setStatus(HttpServletResponse.SC_OK);
                });
        return http.build();
    }*/
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors().and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .requestMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**", "/v3/api-docs", "/users/check-userId", "/users/check-nickname").permitAll()
                .requestMatchers("/users/register", "/users/login_proc", "/users/home", "/verify-token", "/resources/**", "/images/**", "/css/**", "/js/**", "/favicon.ico","/static/**", "/users/myPage").permitAll()  // /users/myPage 접근 허용
                .requestMatchers(HttpMethod.GET, "/api/reviews/{id}","/error").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/reviews/region/**", "/api/reviews/{reviewId}/likes/counts","/api/sub-regions/**","/api/regions/**","/api/reviews/detail/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/reviews/{reviewId}/comments").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/reviews/{reviewId}/comments").hasAuthority("ROLE_USER")
                .requestMatchers(HttpMethod.PUT, "/api/reviews/{reviewId}/comments/{commentId}").hasAuthority("ROLE_USER")
                .requestMatchers(HttpMethod.DELETE, "/api/reviews/{reviewId}/comments/{commentId}").hasAuthority("ROLE_USER")
                .requestMatchers(HttpMethod.GET, "/api/reviews/register","/edit-review/**").hasAuthority("ROLE_USER")
                .requestMatchers(HttpMethod.POST, "/api/reviews/register", "/api/reviews/{reviewId}/likes/**","/edit-review/**").hasAuthority("ROLE_USER")
                .requestMatchers(HttpMethod.PUT, "/api/reviews/{id}").hasAuthority("ROLE_USER")
                .requestMatchers(HttpMethod.DELETE, "/api/reviews/{id}").hasAuthority("ROLE_USER")
                .requestMatchers(HttpMethod.GET,"/api/my-page").hasAuthority("ROLE_USER")
                .requestMatchers("/users/logout").authenticated()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new JwtTokenFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/users/logout", "POST"))
                .logoutSuccessHandler((request, response, authentication) -> {
                    response.setStatus(HttpServletResponse.SC_OK);
                });
        return http.build();
    }


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration().applyPermitDefaultValues();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "X-Requested-With", "accept", "Origin", "Access-Control-Request-Method", "Access-Control-Request-Headers"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}