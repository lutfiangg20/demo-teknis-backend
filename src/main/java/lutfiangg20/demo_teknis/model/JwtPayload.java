package lutfiangg20.demo_teknis.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtPayload {
  private String email;
  private String role;
  private int userId;
}
