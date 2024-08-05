package petstore.api.tests;

import jdk.jfr.Description;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import petstore.api.dto.user.User;
import petstore.api.steps.UserSteps;

import java.util.List;

@TestMethodOrder(OrderAnnotation.class)
public class UserPetStoreTests {

  private static User user = User.defaultUser();

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
    user.setUserStatus(2);
    UserSteps.updateUser(user);
  }

}
