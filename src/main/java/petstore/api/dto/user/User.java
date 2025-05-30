package petstore.api.dto.user;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder(setterPrefix = "set")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class User {
  private String firstName;
  private String lastName;
  private String password;
  private int userStatus;
  private String phone;
  private int id;
  private String email;
  private String username;
}