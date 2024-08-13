package petstore.api.test_data;

import com.github.javafaker.Faker;
import petstore.api.dto.user.User;

public abstract class UserFabric {
    static Faker faker = new Faker();

    public static User defaultUser() {
        return User.builder()
                .setId(112)
                .setUsername("Just User")
                .setFirstName(faker.name().firstName())
                .setLastName(faker.name().lastName())
                .setEmail(faker.internet().emailAddress())
                .setPassword(faker.internet().password())
                .setPhone(faker.phoneNumber().cellPhone())
                .setUserStatus(1)
                .build();
    }

    public static User noNameUser() {
        return User.builder()
                .setId(113)
                .setUsername("noName")
                .build();
    }
}
