package lutfiangg20.demo_teknis.service;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lutfiangg20.demo_teknis.entity.User;
import lutfiangg20.demo_teknis.model.JwtPayload;

@Service
public class JwtService {
  private final String SECRET_KEY = "iniSecretSuperRahasia1234567890!@#$1234";
  public final long REFRESH_TOKEN_EXPIRATION = 1000 * 60 * 60 * 24 * 7;
  public final long ACCESS_TOKEN_EXPIRATION = 1000 * 60 * 60;
  private final SecretKeySpec key = new SecretKeySpec(SECRET_KEY.getBytes(StandardCharsets.UTF_8),
      SignatureAlgorithm.HS256.getJcaName());

  public String generateAccessToken(User user) {
    return Jwts
        .builder()
        .setSubject(user.getEmail())
        .claim("role", "admin")
        .claim("userId", user.getId())
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION))
        .signWith(key)
        .compact();
  }

  public String generateRefreshToken(User user) {
    return Jwts
        .builder()
        .setSubject(user.getEmail())
        .claim("role", "user")
        .claim("userId", user.getId())
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION))
        .signWith(key)
        .compact();
  }

  public Claims validate(String token) {
    try {

      return Jwts
          .parserBuilder()
          .setSigningKey(key)
          .build()
          .parseClaimsJws(token)
          .getBody();
    } catch (JwtException | IllegalArgumentException e) {
      return null;
    }

  }

  public JwtPayload decodePayload(String token) {
    var claims = Jwts.claims();
    try {
      claims = Jwts
          .parserBuilder()
          .setSigningKey(key)
          .build()
          .parseClaimsJws(token)
          .getBody();

    } catch (ExpiredJwtException e) {
      claims = e.getClaims();
    }

    var newPayload = new JwtPayload();
    newPayload.setEmail(claims.get("email", String.class));
    newPayload.setRole(claims.get("role", String.class));
    newPayload.setUserId(((Number) claims.get("userId")).intValue());
    return newPayload;
  }
}
