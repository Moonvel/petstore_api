package petstore.api.tests;

import io.restassured.response.Response;
import jdk.jfr.Description;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import petstore.api.dto.pet.Pet;
import petstore.api.dto.pet.Status;
import petstore.api.steps.PetSteps;
import petstore.api.test_data.PetFabric;
import petstore.api.tools.PetEndPoints;
import petstore.api.tools.Specifications;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import static petstore.api.tools.Specifications.requestSpec;
import static petstore.api.tools.Specifications.requestSpecMultiPart;
import static petstore.api.tools.Specifications.responseSpecError;
import static petstore.api.tools.Specifications.responseSpecOK200;

@TestMethodOrder(OrderAnnotation.class)
public class PetStoreApiTests {

    public static final String baseUrl = "https://petstore.swagger.io/v2";
    public static final String imagePath = "src/test/resources/hhSmile.jpg";
    public static final Long responseTime = 4000L;
    private static Pet pet = PetFabric.defaultPet();
    


    @Test
    @Order(1)
    @Description("Добавление нового питомца")
    public void addNewPetTest() {
        PetSteps.addPet(pet);
    }

    @Test
    @Order(2)
    @Description("Поиск и сравнение питомца созданного в первом тесте")
    public void findPetByIdTest() {
        PetSteps.addPet(pet);
        Pet receivedPet = PetSteps.findPet(pet.getId());
        assertThat(pet)
                .isEqualTo(receivedPet);
    }

    @Test
    @Order(3)
    @Description("Обновление существующего питомца")
    public void updatingPetTest() {
        PetSteps.addPet(pet);
        pet.setStatus(Status.SOLD.getStatus());
        PetSteps.updatingPet(pet);
        Pet receivedPet = PetSteps.findPet(pet.getId());
        assertThat(receivedPet.getStatus())
                .isEqualTo(Status.SOLD.getStatus());
    }

    @Test
    @Order(4)
    @Description("Обновление питомца в магазине через форму")
    public void updatesPetNameAndStatusWithFormDataTest() {
        String newName = "changed";
        Status newStatus = Status.SOLD;
        PetSteps.addPet(pet);
        PetSteps.updatesPetNameAndStatusWithFormData(pet.getId(), newName, newStatus);
        Pet receivedPet = PetSteps.findPet(pet.getId());
        Assertions.assertThat(receivedPet.getName()).isEqualTo(newName);
        Assertions.assertThat(receivedPet.getStatus()).isEqualTo(newStatus.getStatus());
    }

    @Test
    @Order(5)
    @Description("Загрузить изображение питомца по id")
    public void uploadAnImageTest() {
        PetSteps.uploadAnImageTest(pet.getId(), imagePath);
    }

    @Test
    @Order(6)
    @Description("Удаление созданного питомца")
    public void deletePetByIdTest() {
        Specifications.installSpecification(requestSpec(baseUrl), responseSpecError(200));
        given()
                .when()
                .delete(PetEndPoints.DELETE_PET, pet.getId());
    }


    @Test
    @Description("Поиск несуществующего питомца")
    public void nonExistPetTest() {
        Specifications.installSpecification(requestSpec(baseUrl), responseSpecError(404));
        given()
                .when()
                .get(PetEndPoints.FIND_PET, pet.getId());
    }

    @ParameterizedTest
    @EnumSource(Status.class)
    @Description("Поиск питомца по статусу, проверка на соответсвтие статуса питомцев искомому")
    public void findByStatusTest(Status status) {
        Specifications.installSpecification(requestSpec(baseUrl), responseSpecOK200(responseTime));
        Response response = given()
                .when()
                .queryParam("status", status.getStatus())
                .get(PetEndPoints.FIND_BY_STATUS);
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
                .post(PetEndPoints.ADD_PET);
    }

    @Test
    public void addPetWrongHttpMethodeTest() {
        Specifications.installSpecification(requestSpec(baseUrl), responseSpecError(405));
        given()
                .when()
                .get(PetEndPoints.ADD_PET);
    }

}
