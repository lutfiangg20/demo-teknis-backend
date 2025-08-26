package lutfiangg20.demo_teknis.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserWithProfileResponse {
  private int id;
  private String name;
  private String email;
  private String phoneNumber;
  private String dateOfBirth;
  private String address;
  private String bio;
}
