package petstore.api.tests;

import static io.restassured.RestAssured.given;
import static petstore.api.tests.PetStoreApiTests.baseUrl;
import static petstore.api.tests.PetStoreApiTests.responseTime;
import static petstore.api.tools.Specifications.requestSpec;
import static petstore.api.tools.Specifications.responseSpecOK200;

import java.util.ArrayList;
import java.util.List;
import jdk.jfr.Description;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import petstore.api.dto.user.User;
import petstore.api.tools.Specifications;
import petstore.api.tools.UserEndPoints;

public class UserPetStoreTests {

 private static User user = new User();

  @Test
  @Description("Создание пользователя")
  public void createUserTest() {
    Specifications.installSpecification(requestSpec(baseUrl), responseSpecOK200(responseTime));
    user = User.defaultUser();
    given()
        .body(user)
        .when()
        .post(UserEndPoints.createUser);
  }

  @Test
  @Description("Создание списка пользователей")
  public void createUsersTest() {
    Specifications.installSpecification(requestSpec(baseUrl), responseSpecOK200(responseTime));
    List<User> users = new ArrayList<>(List.of(User.defaultUser(), User.noNameUser()));
    given()
        .body(users)
        .when()
        .post(UserEndPoints.createUsersWithList);
  }

  @Test
  @Description("Получение пользователя по username")
  public void getUserByUserNameTest() {
    Specifications.installSpecification(requestSpec(baseUrl), responseSpecOK200(responseTime));
    User recievedUser = given()
        .when()
        .get(UserEndPoints.getUserById, user.getUsername())
        .as(User.class);
    Assertions.assertThat(user).isEqualTo(recievedUser);
  }


}
