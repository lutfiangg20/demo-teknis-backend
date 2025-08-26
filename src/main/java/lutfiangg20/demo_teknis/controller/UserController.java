package lutfiangg20.demo_teknis.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lutfiangg20.demo_teknis.model.RegisterUserRequest;
import lutfiangg20.demo_teknis.model.UserResponse;
import lutfiangg20.demo_teknis.model.UserWithProfileResponse;
import lutfiangg20.demo_teknis.model.WebResponse;
import lutfiangg20.demo_teknis.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

  @Autowired
  private UserService userService;

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public WebResponse<List<UserResponse>> getAllUsers() {
    return WebResponse.<List<UserResponse>>builder().data(userService.getAllUsers()).build();
  }

  @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public WebResponse<UserWithProfileResponse> getUserById(@PathVariable int id) {
    return WebResponse.<UserWithProfileResponse>builder().data(userService.getUserById(id)).build();
  }

  @PostMapping()
  public WebResponse<String> register(@RequestBody RegisterUserRequest request) {
    System.out.println("masuk" + request);
    userService.register(request);
    return WebResponse.<String>builder().data("OK").build();
  }

  @DeleteMapping(path = "/{id}")
  public WebResponse<String> delete(@PathVariable int id) {
    return WebResponse.<String>builder().data(userService.deleteUserById(id)).build();
  }

}
