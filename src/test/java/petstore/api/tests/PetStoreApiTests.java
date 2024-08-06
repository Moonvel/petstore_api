package petstore.api.tests;

import jdk.jfr.Description;
import lombok.SneakyThrows;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import petstore.api.dto.pet.Pet;
import petstore.api.dto.pet.Status;
import petstore.api.steps.PetSteps;
import petstore.api.test_data.PetFabric;

import static org.assertj.core.api.Assertions.assertThat;

public class PetStoreApiTests {


    public static final String baseUrl = "https://petstore.swagger.io/v2";
    public static final String imagePath = "src/test/resources/hhSmile.jpg";
    public static final Long responseTime = 10000L;
    Pet pet;

    @BeforeEach
    public void setUp() {
        pet = PetFabric.defaultPet();
    }


    @Test
    @Description("Добавление нового питомца")
    public void addNewPetTest() {
        PetSteps.addPet(pet);
    }

    @Test
    @Description("Поиск и сравнение питомца созданного в первом тесте")
    public void findPetByIdTest() {
        PetSteps.addPet(pet);
        Pet receivedPet = PetSteps.findPet(pet.getId());
        assertThat(pet)
                .isEqualTo(receivedPet);
    }

    @Test
    @Description("Обновление существующего питомца")
    public void updatePetTest() {
        PetSteps.addPet(pet);
        pet.setStatus(Status.SOLD.getStatus());
        Pet receivedPet = PetSteps.updatingPet(pet);
        assertThat(receivedPet.getStatus())
                .isEqualTo(Status.SOLD.getStatus());
    }

    @Test
    @Description("Обновление питомца в магазине через форму")
    public void updatePetNameAndStatusWithFormDataTest() {
        String newName = "changed";
        Status newStatus = Status.SOLD;
        PetSteps.addPet(pet);
        Pet receivedPet = PetSteps.updatesPetNameAndStatusWithFormData(pet.getId(), newName, newStatus);
        Assertions.assertThat(receivedPet.getName()).isEqualTo(newName);
        Assertions.assertThat(receivedPet.getStatus()).isEqualTo(newStatus.getStatus());
    }

    @Test
    @Description("Загрузить изображение питомца по id")
    public void uploadAnImageTest() {
        PetSteps.uploadAnImageTest(pet.getId(), imagePath);
    }

    @Test
    @Description("Удаление созданного питомца")
    public void deletePetTest() {
        PetSteps.addPet(pet);
        PetSteps.deletePet(pet.getId());
    }


    @Test
    @Description("Поиск несуществующего питомца")
    public void nonExistPetTest() {
        PetSteps.findNonExistPetTest(Long.MAX_VALUE);
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
