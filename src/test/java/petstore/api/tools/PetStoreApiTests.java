package petstore.api.tools;

import static io.restassured.RestAssured.given;
import static petstore.api.tools.Specifications.requestSpec;
import static petstore.api.tools.Specifications.responseSpecError;
import static petstore.api.tools.Specifications.responseSpecOK200;

import org.junit.jupiter.api.Test;
import petstore.api.dto.pet.Pet;

public class PetStoreApiTests {
  public static final String baseUrl = "https://petstore.swagger.io/v2";
  public static final Long responseTime = 2000L;

  @Test
  public void addNewPet() {
    Specifications.installSpecification(requestSpec(baseUrl), responseSpecOK200(responseTime));
    Pet pet = new Pet();
    pet.setName("bobr kurwa");



  }

  @Test
  public void findPetById() {
    Specifications.installSpecification(requestSpec(baseUrl), responseSpecOK200(responseTime));
    Pet pet = given()
        .when().log().uri()
        .get(EndPoints.findPet, 10)
        .as(Pet.class);
  }

  @Test
  public void nonExistPetTest() {
    Specifications.installSpecification(requestSpec(baseUrl), responseSpecError(404));
    given()
        .get(EndPoints.findPet, Integer.MAX_VALUE);
  }
}
