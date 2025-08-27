package lutfiangg20.demo_teknis.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import lutfiangg20.demo_teknis.entity.User;
import lutfiangg20.demo_teknis.model.UserResponse;
import lutfiangg20.demo_teknis.model.UserWithProfile;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
  Optional<User> findByEmail(String email);

  @Query("SELECT new lutfiangg20.demo_teknis.model.UserResponse(u.id, u.name, u.email) FROM User u")
  Page<UserResponse> findAllUserWihtoutPassword(Pageable pageable);

  @Query("""
        SELECT new lutfiangg20.demo_teknis.model.UserResponse(u.id, u.name, u.email) FROM User u WHERE
      LOWER(u.name)
      LIKE LOWER(CONCAT('%', :keyword, '%'))
      OR LOWER(u.email)
      LIKE LOWER(CONCAT('%', :keyword, '%'))
        """)
  Page<UserResponse> searchUserWihtoutPassword(@Param("keyword") String keyword, Pageable pageable);

  @Query("""
          SELECT new lutfiangg20.demo_teknis.model.UserWithProfile(
              u.id,
              u.name,
              u.email,
              COALESCE(p.phoneNumber,''),
              p.dateOfBirth,
              COALESCE(p.address,''),
              COALESCE(p.bio,'')
          )
          FROM User u
          LEFT JOIN u.profile p
          WHERE u.id = :id
      """)

  Optional<UserWithProfile> findUserWithProfile(@Param("id") int id);
}
