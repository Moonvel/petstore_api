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

  public static User defaultUser() {
    return User.builder()
				.setId(111)
				.setUsername("JustUser")
				.setFirstName("Bob")
				.setLastName("White")
        .setEmail("Bob@white.com")
				.setPassword("123")
				.setPhone("+188-66-22")
				.setUserStatus(1)
				.build();
  }

  public static User noNameUser() {
    return User.builder()
				.setId(112)
				.setUsername("NoName")
				.build();
  }
}