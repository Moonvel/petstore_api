package petstore.api.steps;

import io.qameta.allure.Step;
import petstore.api.dto.pet.Pet;
import petstore.api.tools.PetEndPoints;

import static io.restassured.RestAssured.given;

// ToDo Abstract
public abstract class PetSteps {

    // ToDo static
    @Step("Добавление питомца")
    public static void addPet(Pet pet) {
        given()
                .body(pet)
                .when()
                .post(PetEndPoints.ADD_PET)
                .then()
                .statusCode(200);
    }

    @Step("Поиск питомца по id")
    public static Pet findPet(long id) {
        return given()
                .when()
                .get(PetEndPoints.FIND_PET, id)
                .as(Pet.class);
    }

    @Step("Обновление существущего питомца")
    public static void updatingPet(Pet pet) {
        given()
                .body(pet)
                .when()
                .put(PetEndPoints.UPDATING_PET);
    }
}
