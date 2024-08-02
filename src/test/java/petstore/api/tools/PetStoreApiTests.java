package petstore.api.tools;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import static petstore.api.tools.Specifications.requestSpec;
import static petstore.api.tools.Specifications.requestSpecMultiPart;
import static petstore.api.tools.Specifications.responseSpecError;
import static petstore.api.tools.Specifications.responseSpecOK200;

import java.io.File;
import jdk.jfr.Description;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
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
  @Order(3)
  @Description("Обновление существующего питомца")
  public void updatingPet() {
    pet.setStatus("sold");
    Specifications.installSpecification(requestSpec(baseUrl), responseSpecOK200(responseTime));
    given()
        .body(pet)
        .when()
        .put(EndPoints.updatingPet);
  }

  @Test
  @Order(4)
  @Description("Загрузить изображение питомца по id")
  public void uploadPetImageTest() {
    Specifications.installSpecification(requestSpecMultiPart(baseUrl), responseSpecOK200(responseTime));
    given()
        .baseUri("https://petstore.swagger.io/v2")
        .multiPart(new File("src/test/resources/hhSmile.jpg"))
        .when()
        .post(EndPoints.uploadAnImage, pet.getId());
  }

  @Test
  @Order(5)
  @Description("Удаление созданного питомца")
  public void deletePetByIdTest() {
    Specifications.installSpecification(requestSpec(baseUrl), responseSpecError(200));
    given()
        .when()
        .delete(EndPoints.deletePet, pet.getId());
  }


  @Test
  @Order(6)
  @Description("Поиск несуществующего питомца")
  public void nonExistPetTest() {
    Specifications.installSpecification(requestSpec(baseUrl), responseSpecError(404));
    given()
        .when()
        .get(EndPoints.findPet, pet.getId());
  }


}
