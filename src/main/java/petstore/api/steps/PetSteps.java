package petstore.api.steps;

import io.qameta.allure.Step;
import petstore.api.dto.pet.Pet;
import petstore.api.dto.pet.Status;
import petstore.api.tools.PetEndPoints;
import petstore.api.tools.Specifications;

import static io.restassured.RestAssured.given;
import static petstore.api.tools.Specifications.requestSpec;
import static petstore.api.tools.Specifications.requestSpecUrlenc;
import static petstore.api.tools.Specifications.responseSpecOK200;


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

    @Step("Поиск питомца по id")
    public static Pet findPet(long id) {
        Specifications.installSpecification(requestSpec(baseUrl), responseSpecOK200(responseTime));
        return given()
                .when()
                .get(PetEndPoints.FIND_PET, id)
                .as(Pet.class);
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
    public static void updatesPetNameAndStatusWithFormData(long id, String newName, Status newStatus) {
        Specifications.installSpecification(requestSpecUrlenc(baseUrl), responseSpecOK200(responseTime));
        given()
            .formParam("name", newName)
            .formParam("status", newStatus.getStatus())
            .when()
            .post(PetEndPoints.UPDATES_PET_WITH_FORM_DATA, id);
    }
}
