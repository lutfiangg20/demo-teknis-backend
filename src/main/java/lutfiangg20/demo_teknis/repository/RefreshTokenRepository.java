package lutfiangg20.demo_teknis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import lutfiangg20.demo_teknis.entity.RefreshToken;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {
  List<RefreshToken> findByUserIdAndUserAgent(int userId, String userAgent);
}
