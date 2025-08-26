package lutfiangg20.demo_teknis.model;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateUserProfileRequest {
  @Size(max = 100)
  private String name;

  @Size(max = 100)
  private String email;

  @Size(max = 50)
  private String phoneNumber;
  private String dateOfBirth;
  private String address;
  private String bio;

}
