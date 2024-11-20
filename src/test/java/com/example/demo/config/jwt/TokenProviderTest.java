package com.example.demo.config.jwt;

import com.example.demo.domain.User;
import com.example.demo.repository.UserRepository;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import org.hibernate.annotations.Array;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Duration;
import java.util.Date;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@SpringBootTest
public class TokenProviderTest {
    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtProperties jwtProperties;

    //generateToken() 검증테스트
    @DisplayName("generateToken():유저 정보와 만료기간을 전달해 토큰을 만들수 있다.")
    @Test

    void generateToken() {
        //given
        User testUser = userRepository.save(User.builder()
                .email("user@gmail.com")
                .password("test")
                .build());
        //when
        String token = tokenProvider.generateToken(testUser, Duration.ofDays(14));

        //then
        Long userId = Jwts.parser()
                .setSigningKey(jwtProperties.getSecretKey())
                .parseClaimsJws(token)
                .getBody()
                .get("id",Long.class);

        assertThat(userId).isEqualTo(testUser.getId());
    }

    //vaildToken() 검증테스트
    @DisplayName("vaildToken():만료된 토큰인 때에 유효성 검증에 실패한다.")
    @Test
    void vaildToken() {
        //given
        String token = JwtFactory.builder()
                .expiration(new Date(new Date().getTime()-Duration.ofDays(7).toMillis()))
                .build()
                .createToken(jwtProperties);

        //when
        boolean result = tokenProvider.validateToken(token);

        //then
        assertThat(result).isTrue();



    }

    //getAuthenticatition()검증 테스트
    @DisplayName("getAuthenticatition(): 토큰 기반으로 인증정보를 가져올 수 있다.")
    @Test
    void getAuthenticatition() {
        //given
        String userEmail = "user@gmail.com";
        String token = JwtFactory.builder()
                .subject(userEmail)
                .build()
                .createToken(jwtProperties);

        //when
        Authentication authentication = tokenProvider.getAuthentication(token);

        //then
        assertThat((UserDetails) authentication.getPrincipal()).isEqualTo(userEmail);
    }

    //getUserID() 검증 테스트
    @DisplayName("getUserId():토큰으로 유저 ID를 가져올 수 있다.")
    @Test
    void getUserId() {
        Long userId = 1L;
        String token = JwtFactory.builder()
                .claims(Map.of("id",userId))
                .build()
                .createToken(jwtProperties);

        //when
        Long userIdByToken = tokenProvider.getUserId(token);

        //then
        assertThat(userIdByToken).isEqualTo(userId);
    }
    }


