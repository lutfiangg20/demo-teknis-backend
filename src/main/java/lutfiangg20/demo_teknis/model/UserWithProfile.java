package lutfiangg20.demo_teknis.model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserWithProfile {
  private int id;
  private String name;
  private String email;
  private String phoneNumber;
  private LocalDate dateOfBirth;
  private String address;
  private String bio;
}
