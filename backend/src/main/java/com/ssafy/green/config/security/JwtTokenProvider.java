package com.ssafy.green.config.security;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    @Value("spring.jwt.secret")
    private String secretKey;

    public static final long TOKEN_VALIDATION_SECOND = 1000L * 60 * 60 * 5; // 1 Hour

    private final UserDetailsService userDetailsService;

    @PostConstruct
    protected void init(){
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    /**
     * JWT 토큰 생성
     */
    public String createToken(String user_id, long expireTime){    //nickname ->Uid
        Claims claims = Jwts.claims().setSubject(user_id);
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims) //데이터
                .setIssuedAt(now) //토큰 발행일자
                .setExpiration(new Date(now.getTime() + expireTime)) // 만료기한
                .signWith(SignatureAlgorithm.HS256, secretKey) // 암호화알고리즘
                .compact();
    }

    /**
     * AccessToken 생성
     */
    public String generateToken (String user_id){   //username->Uid
        return createToken(user_id, TOKEN_VALIDATION_SECOND);
    }

    /**
     * Jwt 토큰으로 인증정보 조회
     */
    public Authentication getAuthentication(String token){
        UserDetails userDetails = userDetailsService.loadUserByUsername(String.valueOf(this.getUserId(token)));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    //Jwt 토큰에서 회원 구별 정보 추출
    // 현재 nickname 으로 리턴됨, 추후 uid로 변경 (OK)
    public String getUserId(String token){
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    //규칙 추출
    public List<String> getRoles(String token){
        return (List<String>) Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("roles");
    }

    // Request의 Header에서 토큰 파인싱, "X-AUTH-TOKEN" , 헤더이름 커스텀 가능
    public String resolveToken(HttpServletRequest req){
        return req.getHeader("TOKEN");
    }

    public boolean validateToken(String jwtToken){
        try{
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date()); // 기한이 현재보다 전인지
        }catch(Exception e){
            return false;
        }
    }


}
