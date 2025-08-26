package lutfiangg20.demo_teknis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lutfiangg20.demo_teknis.model.LoginUserRequest;
import lutfiangg20.demo_teknis.model.TokenResponse;
import lutfiangg20.demo_teknis.model.WebResponse;
import lutfiangg20.demo_teknis.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
  @Autowired
  private AuthService authService;

  @PostMapping(path = "login", produces = MediaType.APPLICATION_JSON_VALUE)
  public WebResponse<TokenResponse> login(
      @RequestBody LoginUserRequest request,
      @RequestHeader(value = "User-Agent", required = false) String userAgent) {
    return WebResponse.<TokenResponse>builder().data(authService.login(request, userAgent)).build();
  }

  @GetMapping("logout")
  public WebResponse<String> logout(
      @RequestHeader("Authorization") String bearerToken,
      @RequestHeader(value = "User-Agent", required = false) String userAgent) {
    var token = bearerToken.replace("Bearer ", "");
    return WebResponse.<String>builder().data(authService.logout(token, userAgent)).build();

  }

  @GetMapping("check")
  public WebResponse<String> check() {
    return WebResponse.<String>builder().data("You're logged in").build();
  }

}
