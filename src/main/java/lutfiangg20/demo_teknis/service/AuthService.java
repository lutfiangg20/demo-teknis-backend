package lutfiangg20.demo_teknis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import lutfiangg20.demo_teknis.entity.RefreshToken;
import lutfiangg20.demo_teknis.model.LoginUserRequest;
import lutfiangg20.demo_teknis.model.TokenResponse;
import lutfiangg20.demo_teknis.repository.RefreshTokenRepository;
import lutfiangg20.demo_teknis.repository.UserRepository;

@Service
public class AuthService {
  @Autowired
  private UserRepository userRepository;

  @Autowired
  private RefreshTokenRepository refreshTokenRepository;

  @Autowired
  private ValidationService validationService;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private JwtService jwtService;

  public TokenResponse login(
      LoginUserRequest request,
      String userAgent) {
    validationService.validate(request);

    var user = userRepository.findByEmail(request.getEmail())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Email or password wrong"));

    if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
      var refreshToken = new RefreshToken();
      refreshToken.setToken(jwtService.generateRefreshToken(user));
      refreshToken.setUserId(user.getId());
      refreshToken.setUserAgent(userAgent);
      refreshToken.setExpiresAt(jwtService.REFRESH_TOKEN_EXPIRATION);
      refreshTokenRepository.save(refreshToken);

      var accessTokenString = jwtService.generateAccessToken(user);
      return TokenResponse
          .builder()
          .accessToken(accessTokenString)
          .expiredAt(jwtService.ACCESS_TOKEN_EXPIRATION).build();
    } else {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Email or password wrong");
    }
  }

  public String logout(String token, String userAgent) {
    var payload = jwtService.decodePayload(token);
    var userID = payload.getUserId();
    var refreshTokens = refreshTokenRepository.findByUserIdAndUserAgent(userID, userAgent);
    if (refreshTokens.size() > 0) {
      refreshTokenRepository.deleteAll(refreshTokens);
      return "You're logged out";
    }
    return "no refresh token found";
  }

  public String refresh(String token, String userAgent) {
    var payload = jwtService.decodePayload(token);
    var userID = payload.getUserId();
    var refreshTokens = refreshTokenRepository.findByUserIdAndUserAgent(userID, userAgent);
    System.out.println("refreshTokens: " + refreshTokens);
    if (refreshTokens.size() > 0) {
      var accessTokenString = jwtService.generateAccessToken(userRepository.findById(userID).get());
      return accessTokenString;
    }
    return "no refresh token found";
  }

}
