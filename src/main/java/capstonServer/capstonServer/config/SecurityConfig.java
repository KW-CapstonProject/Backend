package capstonServer.capstonServer.config;


import capstonServer.capstonServer.jwt.JwtAuthenticationFilter;
import capstonServer.capstonServer.jwt.JwtEntryPoint;
import capstonServer.capstonServer.jwt.JwtSecurityConfig;
import capstonServer.capstonServer.jwt.JwtTokenProvider;
import capstonServer.capstonServer.security.handler.CustomAccessDeniedHandler;
import capstonServer.capstonServer.security.handler.CustomAuthenticationEntryPoint;
import capstonServer.capstonServer.service.CustomUserDetailsService;
import com.nimbusds.oauth2.sdk.ErrorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.PrintWriter;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate redisTemplate;

    private final CustomUserDetailsService customUserDetailsService;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final JwtSecurityConfig jwtSecurityConfig;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .cors().and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin().disable()  // 기본으로 제공하는 login form 사용 x
                .httpBasic().disable()  //기본 인증방식 사용 X
                .exceptionHandling()
                    .authenticationEntryPoint(customAuthenticationEntryPoint)
                    .accessDeniedHandler(customAccessDeniedHandler)
                    .and()

                .authorizeRequests()
//                    .antMatchers("/",
//                            "/favicon.ico",
//                            "/**/*.png",
//                            "/**/*.gif",
//                            "/**/*.svg",
//                            "/**/*.jpg",
//                            "/**/*.html",
//                            "/**/*.css",
//                            "/**/*.js")
//                        .permitAll()
//                    .antMatchers("/**").permitAll()
                .antMatchers("/**").permitAll()
//                .anyRequest().authenticated()

                // JwtSecurityConfig 클래스 적용
                .and()
                .apply(jwtSecurityConfig)

                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider, redisTemplate, customUserDetailsService), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    // 암호화에 필요한 PasswordEncoder Bean 등록
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}

