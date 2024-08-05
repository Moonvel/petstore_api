package petstore.api.steps;

import io.qameta.allure.Step;
import petstore.api.dto.pet.Pet;
import petstore.api.tools.PetEndPoints;

import static io.restassured.RestAssured.given;

// ToDo Abstract
public class ApiSteps {

    // ToDo static
    @Step("Добавление питомца")
    public Pet addNewPet(Pet pet) {
        given()
                .body(pet)
                .when()
                .post(PetEndPoints.addPet)
                .then()
                .statusCode(200);
        return pet;
    }

    @Step("Поиск питомца по id")
    public Pet findPetById(long id) {
        return given()
                .when()
                .get(PetEndPoints.findPet, id)
                .as(Pet.class);
    }

    @Step("Обновление существущего питомца")
    public void updatingPet(Pet pet) {
        given()
                .body(pet)
                .when()
                .put(PetEndPoints.updatingPet);
    }
}
