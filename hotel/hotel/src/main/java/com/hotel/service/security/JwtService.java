package com.hotel.service.security;

import com.hotel.model.dto.ClaimsDto;
import com.hotel.model.entity.User;
import com.hotel.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtService {
//TODO: SECRET to application.properties (NOWDAYS-KEYSTORAGE)
    public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";
    private final UserRepository userRepository;

    public String generateToken(String userName) {
        Map<String, Object> claims = new HashMap<>();
        User user = userRepository.findByUsername(userName).orElseThrow(() -> new UsernameNotFoundException("User was not found"));
        ClaimsDto claimsDto = new ClaimsDto(user.getUsername(),user.getId(),user.getRole().getName());
        String username = claimsDto.getUsername();
        claims.put("username", username);
        claims.put("id", claimsDto.getId());
        claims.put("role", claimsDto.getRole());

        return createToken(claims, userName);
    }

    private String createToken(Map<String, Object> claims, String userName) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60*10000))
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


    public ClaimsDto parseToken(String token) {

        Claims claims = extractAllClaims(token);
        String username = claims.get("username", String.class);
        Integer id = claims.get("id", Integer.class);
        String role = claims.get("role", String.class);
      return new ClaimsDto(username,id,role);
    }
}

