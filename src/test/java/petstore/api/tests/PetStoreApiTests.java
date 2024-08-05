package petstore.api.tests;

import io.restassured.response.Response;
import jdk.jfr.Description;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import petstore.api.dto.pet.Pet;
import petstore.api.dto.pet.Status;
import petstore.api.steps.ApiSteps;
import petstore.api.test_data.PetFabric;
import petstore.api.tools.PetEndPoints;
import petstore.api.tools.Specifications;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import static petstore.api.tools.Specifications.requestSpec;
import static petstore.api.tools.Specifications.requestSpecMultiPart;
import static petstore.api.tools.Specifications.requestSpecUrlenc;
import static petstore.api.tools.Specifications.responseSpecError;
import static petstore.api.tools.Specifications.responseSpecOK200;

@TestMethodOrder(OrderAnnotation.class)
public class PetStoreApiTests {

    public static final String baseUrl = "https://petstore.swagger.io/v2";
    public static final Long responseTime = 4000L;
    private static Pet pet = PetFabric.defaultPet();
    ApiSteps steps = new ApiSteps();

    @Test
    @Order(1)
    @Description("Добавление нового питомца")
    public void addNewPetTest() {
        Specifications.installSpecification(requestSpec(baseUrl), responseSpecOK200(responseTime));
        pet = steps.addNewPet(pet);
    }

    @Test
    @Order(2)
    @Description("Поиск и сравнение питомца созданного в первом тесте")
    public void findPetByIdTest() {
        Specifications.installSpecification(requestSpec(baseUrl), responseSpecOK200(responseTime));
        Pet addedPet = steps.addNewPet(pet);
        Pet receivedPet = steps.findPetById(addedPet.getId());
        assertThat(addedPet)
                .isEqualTo(receivedPet);
    }

    @Test
    @Order(3)
    @Description("Обновление существующего питомца")
    public void updatingPet() {
        Specifications.installSpecification(requestSpec(baseUrl), responseSpecOK200(responseTime));
        Pet addedPet = steps.addNewPet(pet);
        addedPet.setStatus(Status.SOLD.getStatus());
        steps.updatingPet(addedPet);
        Pet changedPet = steps.findPetById(addedPet.getId());
        assertThat(changedPet.getStatus())
                .isEqualTo(Status.SOLD.getStatus());
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
                .post(PetEndPoints.updatesPetWithFormData, 111);
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
                .post(PetEndPoints.uploadAnImage, pet.getId());
    }

    @Test
    @Order(6)
    @Description("Удаление созданного питомца")
    public void deletePetByIdTest() {
        Specifications.installSpecification(requestSpec(baseUrl), responseSpecError(200));
        given()
                .when()
                .delete(PetEndPoints.deletePet, pet.getId());
    }


    @Test
    @Description("Поиск несуществующего питомца")
    public void nonExistPetTest() {
        Specifications.installSpecification(requestSpec(baseUrl), responseSpecError(404));
        given()
                .when()
                .get(PetEndPoints.findPet, pet.getId());
    }

    @ParameterizedTest
    @EnumSource(Status.class)
    @Description("Поиск питомца по статусу, проверка на соответсвтие статуса питомцев искомому")
    public void findByStatusTest(Status status) {
        Specifications.installSpecification(requestSpec(baseUrl), responseSpecOK200(responseTime));
        Response response = given()
                .when()
                .queryParam("status", status.getStatus())
                .get(PetEndPoints.findByStatus);
        Pet[] pets = response.as(Pet[].class);
        assertThat(pets)
                .allMatch(pet -> pet.getStatus().equals(status.getStatus()));
    }

    @Test
    @Description("Использование некорректного body при добавлении питомца")
    public void addPetWrongBodyTest() {
        Specifications.installSpecification(requestSpec(baseUrl), responseSpecError(500));
        given()
                .body("[]")
                .when()
                .post(PetEndPoints.addPet);
    }

    @Test
    public void addPetWrongHttpMethodeTest() {
        Specifications.installSpecification(requestSpec(baseUrl), responseSpecError(405));
        given()
                .when()
                .get(PetEndPoints.addPet);
    }

}
