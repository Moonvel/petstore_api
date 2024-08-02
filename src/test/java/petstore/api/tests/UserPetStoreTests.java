package petstore.api.tests;

import static io.restassured.RestAssured.given;
import static petstore.api.tests.PetStoreApiTests.baseUrl;
import static petstore.api.tests.PetStoreApiTests.responseTime;
import static petstore.api.tools.Specifications.requestSpec;
import static petstore.api.tools.Specifications.responseSpecOK200;

import jdk.jfr.Description;
import org.junit.jupiter.api.Test;
import petstore.api.dto.user.User;
import petstore.api.tools.Specifications;
import petstore.api.tools.UserEndPoints;

public class UserPetStoreTests {



  User user = User.defaultUser();
  @Test
  @Description("Создание пользователя")
  public void createUserTest() {
    Specifications.installSpecification(requestSpec(baseUrl), responseSpecOK200(responseTime));
    given().body(user).post(UserEndPoints.createUser);
  }
}
