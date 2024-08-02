package petstore.api.tools;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import static petstore.api.tools.Specifications.requestSpec;
import static petstore.api.tools.Specifications.responseSpecError;
import static petstore.api.tools.Specifications.responseSpecOK200;

import jdk.jfr.Description;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import petstore.api.dto.pet.Pet;

@TestMethodOrder(OrderAnnotation.class)
public class PetStoreApiTests {

  public static final String baseUrl = "https://petstore.swagger.io/v2";
  public static final Long responseTime = 4000L;

  private static Pet pet = new Pet();

  @Test
  @Order(1)
  @Description("Добавление нового питомца")
  public void addNewPet() {
    Specifications.installSpecification(requestSpec(baseUrl), responseSpecOK200(responseTime));
    pet = Pet.defaultPet();
    given()
        .body(pet)
        .when()
        .post(EndPoints.addPet);
  }

  @Test
  @Order(2)
  @Description("Поиск и сравнение питомца созданного в первом тесте")
  public void findPetByIdTest() {
    Specifications.installSpecification(requestSpec(baseUrl), responseSpecOK200(responseTime));
    Pet receivedPet = given()
        .when()
        .get(EndPoints.findPet, Pet.defaultPet().getId())
        .as(Pet.class);
    Assertions.assertThat(pet).isEqualTo(receivedPet);
  }

  @Test
  @Description("Поиск несуществующего питомца")
  public void nonExistPetTest() {
    Specifications.installSpecification(requestSpec(baseUrl), responseSpecError(404));
    given()
        .get(EndPoints.findPet, Integer.MAX_VALUE);
  }
}
