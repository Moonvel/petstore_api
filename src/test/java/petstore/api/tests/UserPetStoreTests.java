package petstore.api.tests;

import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
  @DisplayName("Создание пользователя")
  public void createUserTest() {
    UserSteps.createUser(user);
  }

  @Test
  @DisplayName("Получение пользователя по username")
  public void getUserByUserNameTest() {
    UserSteps.createUser(user);
    User recievedUser = UserSteps.getUserByUserName(user);
    Assertions.assertThat(user)
                .isEqualTo(recievedUser);
  }

  @Test
  @DisplayName("Создание списка пользователей")
  public void createUsersWithListTest() {
    List<User> users = List.of(UserFabric.defaultUser(), UserFabric.noNameUser());
    UserSteps.createUsersWithList(users);
  }

  @DisplayName("Обновление пользователя")
  public void updateUserTest() {
    String newFirstName = "nameWasChanged";
    user.setFirstName(newFirstName);
    User recievedUser = UserSteps.updateUser(user);
    Assertions.assertThat(recievedUser.getFirstName()).isEqualTo(newFirstName);
  }

  @Test
  @DisplayName("Удаление пользователя")
  public void deleteUserTest() {
    UserSteps.createUser(user);
    UserSteps.deleteUser(user.getUsername());
  }

  @Test
  @DisplayName("Login")
  public void loginTest() {
    UserSteps.login(user.getUsername(), user.getPassword());
  }

  @Test
  @DisplayName("Logout")
  public void logoutTest() {
    UserSteps.logout();
  }

}
