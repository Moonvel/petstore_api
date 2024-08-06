package petstore.api.test_data;

import petstore.api.dto.user.User;

public abstract class UserFabric {
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
				  .setUsername("noName")
				  .build();
	}
}
