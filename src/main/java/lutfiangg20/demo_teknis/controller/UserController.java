package lutfiangg20.demo_teknis.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lutfiangg20.demo_teknis.model.RegisterUserRequest;
import lutfiangg20.demo_teknis.model.UpdateUserProfileRequest;
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
  public WebResponse<Page<UserResponse>> getAllUsers(
      @RequestParam(required = false) String search,
      @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
    if (search != null && !search.isBlank()) {
      return WebResponse.<Page<UserResponse>>builder().data(userService.searchUsers(search, pageable)).build();
    }
    return WebResponse.<Page<UserResponse>>builder().data(userService.getAllUsers(pageable)).build();
  }

  @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public WebResponse<UserWithProfileResponse> getUserById(@PathVariable int id) {
    return WebResponse.<UserWithProfileResponse>builder().data(userService.getUserById(id)).build();
  }

  @PostMapping()
  public WebResponse<String> register(@RequestBody RegisterUserRequest request) {
    userService.register(request);
    return WebResponse.<String>builder().data("OK").build();
  }

  @PutMapping(path = "/{id}")
  public WebResponse<String> update(@PathVariable int id, @RequestBody UpdateUserProfileRequest request) {
    return WebResponse.<String>builder().data(userService.updateUserProfile(id, request)).build();
  }

  @DeleteMapping(path = "/{id}")
  public WebResponse<String> delete(@PathVariable int id) {
    return WebResponse.<String>builder().data(userService.deleteUserById(id)).build();
  }

}
