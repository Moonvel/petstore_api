package petstore.api.steps;

import static io.restassured.RestAssured.given;

import io.qameta.allure.Step;
import petstore.api.dto.pet.Pet;
import petstore.api.tools.PetEndPoints;

public class ApiSteps {

	@Step("Добавление питомца")
	public Pet addNewPet(Pet pet) {
		given()
				.body(pet)
				.when()
				.post(PetEndPoints.addPet);

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
