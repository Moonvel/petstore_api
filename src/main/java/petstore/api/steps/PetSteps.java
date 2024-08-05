package petstore.api.steps;

import static io.restassured.RestAssured.given;
import static petstore.api.spec.Specifications.requestSpec;
import static petstore.api.spec.Specifications.requestSpecMultiPart;
import static petstore.api.spec.Specifications.requestSpecUrlenc;
import static petstore.api.spec.Specifications.responseSpecError;
import static petstore.api.spec.Specifications.responseSpecOK200;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import java.io.File;
import petstore.api.dto.pet.Pet;
import petstore.api.dto.pet.Status;
import petstore.api.endpoints.PetEndPoints;
import petstore.api.spec.Specifications;


public abstract class PetSteps {

    public static final String baseUrl = "https://petstore.swagger.io/v2";
    public static final Long responseTime = 4000L;

    @Step("Добавление питомца")
    public static void addPet(Pet pet) {
        Specifications.installSpecification(requestSpec(baseUrl), responseSpecOK200(responseTime));
        given()
                .body(pet)
                .when()
                .post(PetEndPoints.ADD_PET)
                .then()
                .statusCode(200);
    }

    @Step("Поиск питомца")
    public static Pet findPet(long petId) {
        Specifications.installSpecification(requestSpec(baseUrl), responseSpecOK200(responseTime));
        return given()
                .when()
            .get(PetEndPoints.FIND_PET, petId)
                .as(Pet.class);
    }

    @Step("Поиск несуществующего питомца")
    public static void findNonExistPetTest(long petId) {
        Specifications.installSpecification(requestSpec(baseUrl), responseSpecError(404));
        given()
            .when()
            .get(PetEndPoints.FIND_PET, petId);
    }

    @Step("Обновление существущего питомца")
    public static void updatingPet(Pet pet) {
        Specifications.installSpecification(requestSpec(baseUrl), responseSpecOK200(responseTime));
        given()
                .body(pet)
                .when()
                .put(PetEndPoints.UPDATING_PET);
    }

    @Step("Обновление имени и статуса существующего питомца")
    public static void updatesPetNameAndStatusWithFormData(long petId, String newName,
        Status newStatus) {
        Specifications.installSpecification(requestSpecUrlenc(baseUrl), responseSpecOK200(responseTime));
        given()
            .formParam("name", newName)
            .formParam("status", newStatus.getStatus())
            .when()
            .post(PetEndPoints.UPDATES_PET_WITH_FORM_DATA, petId);
    }

    @Step("Загрузка изображения питомца")
    public static void uploadAnImageTest(long petId, String imagePath) {
        Specifications.installSpecification(requestSpecMultiPart(baseUrl),
            responseSpecOK200(responseTime));
        given()
            .multiPart(new File(imagePath))
            .when()
            .post(PetEndPoints.UPLOAD_AN_IMAGE, petId);
    }

    @Step("Удаление существущего питомца")
    public static void deletePet(long petId) {
        Specifications.installSpecification(requestSpec(baseUrl), responseSpecError(200));
        given()
            .when()
            .delete(PetEndPoints.DELETE_PET, petId);
    }

    @Step("Поиск питомцев по статусу")
    public static Pet[] findPetsByStatus(Status status) {
        Specifications.installSpecification(requestSpec(baseUrl), responseSpecOK200(responseTime));
        Response response = given()
            .when()
            .queryParam("status", status.getStatus())
            .get(PetEndPoints.FIND_PETS_BY_STATUS);
        return response.as(Pet[].class);
    }

    @Step("Использование некорректного body при добавлении питомца")
    public static void addPetWrongBody() {
        Specifications.installSpecification(requestSpec(baseUrl), responseSpecError(500));
        given()
            .body("[]")
            .when()
            .post(PetEndPoints.ADD_PET);
    }

    @Step("Добавление питомца используя некорректный http метод")
    public static void addPetWrongHttpMethod() {
        Specifications.installSpecification(requestSpec(baseUrl), responseSpecError(405));
        given()
            .when()
            .get(PetEndPoints.ADD_PET);
    }
}
