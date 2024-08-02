package petstore.api.tools;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import static petstore.api.tools.Specifications.requestSpec;
import static petstore.api.tools.Specifications.requestSpecMultiPart;
import static petstore.api.tools.Specifications.requestSpecUrlenc;
import static petstore.api.tools.Specifications.responseSpecError;
import static petstore.api.tools.Specifications.responseSpecOK200;

import io.restassured.response.Response;
import java.io.File;
import jdk.jfr.Description;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import petstore.api.dto.pet.Pet;
import petstore.api.dto.pet.Status;

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
    assertThat(pet).isEqualTo(receivedPet);
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
  @Description("Обновление питомца в магазине через форму")
  public void updatesPetWithFormDataTest() {
    Specifications.installSpecification(requestSpecUrlenc(baseUrl), responseSpecOK200(responseTime));
    given()
        .formParam("name", "changed")
        .formParam("status", "dead")
        .when()
        .post(EndPoints.updatesPetWithFormData, 111);
  }

  @Test
  @Order(5)
  @Description("Загрузить изображение питомца по id")
  public void uploadPetImageTest() {
    Specifications.installSpecification(requestSpecMultiPart(baseUrl),
        responseSpecOK200(responseTime));
    given()
        .baseUri("https://petstore.swagger.io/v2")
        .multiPart(new File("src/test/resources/hhSmile.jpg"))
        .when()
        .post(EndPoints.uploadAnImage, pet.getId());
  }

  @Test
  @Order(6)
  @Description("Удаление созданного питомца")
  public void deletePetByIdTest() {
    Specifications.installSpecification(requestSpec(baseUrl), responseSpecError(200));
    given()
        .when()
        .delete(EndPoints.deletePet, pet.getId());
  }


  @Test
  @Description("Поиск несуществующего питомца")
  public void nonExistPetTest() {
    Specifications.installSpecification(requestSpec(baseUrl), responseSpecError(404));
    given()
        .when()
        .get(EndPoints.findPet, pet.getId());
  }

  @ParameterizedTest
  @EnumSource(Status.class)
  @Description("Поиск питомца по статусу, проверка на соответсвтие статуса питомцев искомому")
  public void findByStatusTest(Status status) {
    Specifications.installSpecification(requestSpec(baseUrl), responseSpecOK200(responseTime));
    Response response = given()
        .when()
        .queryParam("status", status.getStatus())
        .get(EndPoints.findByStatus);
    Pet[] pets = response.as(Pet[].class);
    assertThat(pets)
        .isNotEmpty()
        .allMatch(pet -> pet.getStatus().equals(status.getStatus()));
  }



}
