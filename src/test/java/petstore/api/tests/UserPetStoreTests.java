package petstore.api.tests;

import jdk.jfr.Description;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import petstore.api.dto.user.User;
import petstore.api.steps.UserSteps;

import java.util.List;

public class UserPetStoreTests {
  User user;


  @BeforeEach
  public void setUp() {
    user = User.defaultUser();
  }

  @Test
  @Description("Создание пользователя")
  public void createUserTest() {
    UserSteps.createUser(user);
  }

  @Test
  @Description("Получение пользователя по username")
  public void getUserByUserNameTest() {
    UserSteps.createUser(user);
    User recievedUser = UserSteps.getUserByUserName(user.getUsername());
    Assertions.assertThat(user)
                .isEqualTo(recievedUser);
  }

  @Test
  @Description("Создание списка пользователей")
  public void createUsersWithListTest() {
    List<User> users = List.of(User.defaultUser(), User.noNameUser());
    UserSteps.createUsersWithList(users);
  }


  @Test
  @Description("Обновление пользователя")
  public void updateUserTest() {
    String newFirstName = "nameWasChanged";
    user.setFirstName(newFirstName);
    User recievedUser = UserSteps.updateUser(user);
    Assertions.assertThat(recievedUser.getFirstName())
              .isEqualTo(newFirstName);
  }

}
