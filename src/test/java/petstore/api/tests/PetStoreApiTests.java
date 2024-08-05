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

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import static petstore.api.tools.Specifications.requestSpec;
import static petstore.api.tools.Specifications.responseSpecError;
import static petstore.api.tools.Specifications.responseSpecOK200;

@TestMethodOrder(OrderAnnotation.class)
public class PetStoreApiTests {

    public static final String baseUrl = "https://petstore.swagger.io/v2";
    public static final String imagePath = "src/test/resources/hhSmile.jpg";
    public static final Long responseTime = 10000L;
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
    public void updatePetTest() {
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
    public void updatePetNameAndStatusWithFormDataTest() {
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
    public void deletePetTest() {
        PetSteps.addPet(pet);
        PetSteps.deletePet(pet.getId());
    }


    @Test
    @Description("Поиск несуществующего питомца")
    public void nonExistPetTest() {
        PetSteps.addPet(pet);
        PetSteps.deletePet(pet.getId());
        PetSteps.findNonExistPetTest(pet.getId());
    }

    @ParameterizedTest
    @EnumSource(Status.class)
    @Description("Поиск питомца по статусу, проверка на соответсвтие статуса питомцев искомому")
    public void findPetsByStatusTest(Status status) {
        Pet[] pets = PetSteps.findPetsByStatus(status);
        assertThat(pets)
                .allMatch(pet -> pet.getStatus().equals(status.getStatus()));
    }

    @Test
    @Description("Использование некорректного body при добавлении питомца")
    public void addPetWrongBodyTest() {
        PetSteps.addPetWrongBody();
    }

    @Test
    public void addPetWrongHttpMethodTest() {
        PetSteps.addPetWrongHttpMethod();
    }

}
