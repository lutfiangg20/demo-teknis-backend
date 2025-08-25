package lutfiangg20.demo_teknis.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginUserRequest {
  @NotBlank
  @Size(max = 100)
  private String email;

  @NotBlank
  @Size(max = 255)
  private String password;
}
