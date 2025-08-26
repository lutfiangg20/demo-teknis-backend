package lutfiangg20.demo_teknis.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import jakarta.transaction.Transactional;
import lutfiangg20.demo_teknis.entity.User;
import lutfiangg20.demo_teknis.model.RegisterUserRequest;
import lutfiangg20.demo_teknis.model.UserResponse;
import lutfiangg20.demo_teknis.model.UserWithProfileResponse;
import lutfiangg20.demo_teknis.repository.UserRepository;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private ValidationService validationService;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Transactional
  public void register(RegisterUserRequest request) {
    validationService.validate(request);

    if (userRepository.findByEmail(request.getEmail()).isPresent()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already registered");
    }

    var user = new User();
    user.setEmail(request.getEmail());
    user.setName(request.getName());
    user.setPassword(passwordEncoder.encode(request.getPassword()));

    userRepository.save(user);
  }

  public List<UserResponse> getAllUsers() {
    return userRepository.findAllUserWihtoutPassword();
  }

  public UserWithProfileResponse getUserById(int id) {
    var userOptional = userRepository.findUserWithProfile(id);
    System.out.println("get user by id" + userOptional);

    if (userOptional.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
    }

    var user = userOptional.get();

    return new UserWithProfileResponse(
        user.getId(),
        user.getName(),
        user.getEmail(),
        user.getPhoneNumber(),
        user.getDateOfBirth() != null ? user.getDateOfBirth().toString() : "",
        user.getAddress(),
        user.getBio());

  }

  public String deleteUserById(int id) {
    if (userRepository.existsById(id)) {
      userRepository.deleteById(id);
      return "User deleted successfully";
    } else {
      return "user not found";
    }
  }

}
