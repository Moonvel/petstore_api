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
import static petstore.api.spec.Specifications.requestSpec;
import static petstore.api.spec.Specifications.requestSpecMultiPart;
import static petstore.api.spec.Specifications.requestSpecUrlenc;
import static petstore.api.spec.Specifications.responseSpecError;
import static petstore.api.spec.Specifications.responseSpecOK200;


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

        Awaitility.await()
                  .atMost(20, TimeUnit.SECONDS)
                  .pollInterval(500, TimeUnit.MILLISECONDS)
                  .until(() -> {
                      Pet recievedPet = PetSteps.findPet(pet.getId())
                                                .as(Pet.class);
                      return recievedPet.equals(pet);
                  });
    }


    @Step("Поиск питомца")
    public static Response findPet(long petId) {
        Specifications.installSpecification(requestSpec(baseUrl), responseSpecOK200(responseTime));
        return given()
                .when()
                .get(PetEndPoints.FIND_PET, petId);
    }

    @Step("Поиск несуществующего питомца")
    public static void findNonExistPetTest(long petId) {
        Specifications.installSpecification(requestSpec(baseUrl), responseSpecError(404));
        given()
            .when()
            .get(PetEndPoints.FIND_PET, petId);
    }

    @SneakyThrows
    @Step("Обновление существущего питомца")
    public static Pet updatingPet(Pet pet) {
        Specifications.installSpecification(requestSpec(baseUrl), responseSpecOK200(responseTime));
        given()
                .body(pet)
                .when()
                .put(PetEndPoints.UPDATING_PET);

        /*
		  Ожидание обновления пользователя в базе данных для дальнейших корректных проверок
 		 */
        Awaitility.await()
                  .atMost(20, TimeUnit.SECONDS)
                  .pollInterval(500, TimeUnit.MILLISECONDS)
                  .until(() -> {
                      Pet recievedPet = PetSteps.findPet(pet.getId())
                                                .as(Pet.class);
                      return recievedPet.equals(pet);
                  });
        return PetSteps.findPet(pet.getId())
                       .as(Pet.class);
    }

    @SneakyThrows
    @Step("Обновление имени и статуса существующего питомца")
    public static void updatesPetNameAndStatusWithFormData(long petId, String newName, Status newStatus) {
        Specifications.installSpecification(requestSpecUrlenc(baseUrl), responseSpecOK200(responseTime));
        given()
            .formParam("name", newName)
            .formParam("status", newStatus.getStatus())
            .when()
            .post(PetEndPoints.UPDATES_PET_WITH_FORM_DATA, petId);

        /*
		  Ожидание обновления питомца в базе данных для дальнейших корректных проверок
 		 */
        Awaitility.await()
                  .atMost(10, TimeUnit.SECONDS)
                  .pollInterval(500, TimeUnit.MILLISECONDS)
                  .until(() -> {
                      Pet recievedPet = PetSteps.findPet(petId)
                                                .as(Pet.class);
                      return recievedPet.getName()
                                        .equals(newName);
                  });
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
