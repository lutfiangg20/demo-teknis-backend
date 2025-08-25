package lutfiangg20.demo_teknis.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import lutfiangg20.demo_teknis.entity.User;
import lutfiangg20.demo_teknis.model.UserResponse;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
  Optional<User> findByEmail(String email);

  @Query("SELECT new lutfiangg20.demo_teknis.model.UserResponse(u.id, u.name, u.email) FROM User u")
  List<UserResponse> findAllUserWihtoutPassword();
}
