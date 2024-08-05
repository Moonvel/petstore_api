package petstore.api.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
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

  public static User defaultUser() {
    return User.builder()
				.id(111)
				.username("JustUser")
				.firstName("Bob")
				.lastName("White")
        .email("Bob@white.com")
				.password("123")
				.phone("+188-66-22")
				.userStatus(1)
				.build();
  }

  public static User noNameUser() {
    return User.builder().id(112).username("NoName").build();
  }
}