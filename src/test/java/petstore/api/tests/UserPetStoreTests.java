package petstore.api.tests;

import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import petstore.api.dto.user.User;
import petstore.api.steps.UserSteps;
import petstore.api.test_data.UserFabric;

import java.util.List;

@Owner("Moonvel")
public class UserPetStoreTests {
  User user;


  @BeforeEach
  public void setUp() {
    user = UserFabric.defaultUser();
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
    User recievedUser = UserSteps.getUserByUserName(user);
    Assertions.assertThat(user)
                .isEqualTo(recievedUser);
  }

  @Test
  @Description("Создание списка пользователей")
  public void createUsersWithListTest() {
    List<User> users = List.of(UserFabric.defaultUser(), UserFabric.noNameUser());
    UserSteps.createUsersWithList(users);
  }

  @Description("Обновление пользователя")
  public void updateUserTest() {
    String newFirstName = "nameWasChanged";
    user.setFirstName(newFirstName);
    User recievedUser = UserSteps.updateUser(user);
    Assertions.assertThat(recievedUser.getFirstName()).isEqualTo(newFirstName);
  }

  @Test
  @Description("Удаление пользователя")
  public void deleteUserTest() {
    UserSteps.createUser(user);
    UserSteps.deleteUser(user.getUsername());
  }

  @Test
  @Description("Login")
  public void loginTest() {
    UserSteps.login(user.getUsername(), user.getPassword());
  }

  @Test
  @Description("Logout")
  public void logoutTest() {
    UserSteps.logout();
  }

}
