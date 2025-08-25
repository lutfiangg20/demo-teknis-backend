package lutfiangg20.demo_teknis.service;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lutfiangg20.demo_teknis.entity.User;

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
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION))
        .signWith(key)
        .compact();
  }

  public String generateRefreshToken(User user) {
    return Jwts
        .builder()
        .setSubject(user.getEmail())
        .claim("role", "admin")
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION))
        .signWith(key)
        .compact();
  }
}
