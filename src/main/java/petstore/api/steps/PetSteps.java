package petstore.api.steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import lombok.SneakyThrows;
import org.awaitility.Awaitility;
import petstore.api.dto.pet.Pet;
import petstore.api.dto.pet.Status;
import petstore.api.endpoints.PetEndPoints;
import petstore.api.props.PropsHelper;
import petstore.api.spec.Specifications;

import java.io.File;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;
import static petstore.api.spec.Specifications.*;


public abstract class PetSteps {
    static PropsHelper propsHelper = new PropsHelper();

    public static final String baseUrl = propsHelper.getProperty("baseUrl");
    public static final Long responseTime = 10000L;

    @SneakyThrows
    @Step("Добавление питомца")
    public static void addPet(Pet pet) {
        Specifications.installSpecification(requestSpec(baseUrl), responseSpecOK200(responseTime));
        given()
                .body(pet)
                .when()
                .post(PetEndPoints.ADD_PET);
    }

    @Step("Поиск питомца")
    public static Pet findPet(Pet pet) {
        Specifications.installSpecification(requestSpec(baseUrl), responseSpecOK200(responseTime));

        final Pet[] receivedPetHolder = new Pet[1];
        Awaitility.await()
                  .atMost(10, TimeUnit.SECONDS)
                  .pollDelay(1000, TimeUnit.MILLISECONDS)
                  .pollInterval(1000, TimeUnit.MILLISECONDS)
                  .until(() -> {
                      Pet recievedPet = given()
                              .when()
                              .get(PetEndPoints.FIND_PET, pet.getId())
                              .as(Pet.class);
                      receivedPetHolder[0] = recievedPet;
                      return recievedPet.equals(pet);
                  });

        return receivedPetHolder[0];
    }

    @Step("Поиск несуществующего питомца")
    public static void findNonExistPetTest(Pet pet) {
        Specifications.installSpecification(requestSpec(baseUrl), responseSpecError(404));
        given()
            .when()
            .get(PetEndPoints.FIND_PET, pet.getId());
    }

    @SneakyThrows
    @Step("Обновление существущего питомца")
    public static Pet updatingPet(Pet pet) {
        Specifications.installSpecification(requestSpec(baseUrl), responseSpecOK200(responseTime));

        given()
                .body(pet)
                .when()
                .put(PetEndPoints.UPDATING_PET);

        return PetSteps.findPet(pet);
    }

    @SneakyThrows
    @Step("Обновление имени и статуса существующего питомца")
    public static void updatesPetNameAndStatusWithFormData(Pet pet, String newName, Status newStatus) {
        Specifications.installSpecification(requestSpec(baseUrl), responseSpecOK200(responseTime));
        findPet(pet);
        Specifications.installSpecification(requestSpecUrlenc(baseUrl), responseSpecOK200(responseTime));

        given()
            .formParam("name", newName)
            .formParam("status", newStatus.getStatus())
            .when()
            .post(PetEndPoints.UPDATES_PET_WITH_FORM_DATA, pet.getId());

        /*
		  Ожидание обновления питомца в базе данных для дальнейших корректных проверок
 		 */
        Awaitility.await()
                  .atMost(10, TimeUnit.SECONDS)
                  .pollDelay(1000, TimeUnit.MILLISECONDS)
                  .pollInterval(1000, TimeUnit.MILLISECONDS)
                  .until(() -> {
                      Pet recievedPet = PetSteps.findPet(pet);
                      return recievedPet.getName()
                                        .equals(newName);
                  });
    }

    @Step("Загрузка изображения питомца")
    public static void uploadAnImageTest(long petId, String imagePath) {
        Specifications.installSpecification(requestSpecMultiPart(baseUrl), responseSpecOK200(responseTime));
        given()
            .multiPart(new File(imagePath))
            .when()
            .post(PetEndPoints.UPLOAD_AN_IMAGE, petId);
    }

    @Step("Удаление существущего питомца")
    public static void deletePet(Pet pet) {
        Specifications.installSpecification(requestSpec(baseUrl), responseSpecError(200));
        Awaitility.await()
                  .atMost(10, TimeUnit.SECONDS)
                  .pollDelay(2000, TimeUnit.MILLISECONDS)
                  .pollInterval(1000, TimeUnit.MILLISECONDS)
                  .until(() -> given()
                          .when()
                          .delete(PetEndPoints.DELETE_PET, pet.getId())
                          .getStatusCode() == 200);

    }

    @Step("Поиск питомца со статусом {status}")
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
