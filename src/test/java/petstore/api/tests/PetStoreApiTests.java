package petstore.api.tests;

import io.qameta.allure.Description;
import io.qameta.allure.Flaky;
import io.qameta.allure.Owner;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import petstore.api.dto.pet.Pet;
import petstore.api.dto.pet.Status;
import petstore.api.props.PropsHelper;
import petstore.api.steps.PetSteps;
import petstore.api.test_data.PetFabric;

import static org.assertj.core.api.Assertions.assertThat;

@Owner("Moonvel")
public class PetStoreApiTests {
    static PropsHelper propsHelper = new PropsHelper();
    public static final String imagePath = propsHelper.getProperty("testImg");
    Pet pet; Pet changedPet;

    SoftAssertions softAssertions = new SoftAssertions();


    @BeforeEach
    public void setUp() {
        pet = PetFabric.defaultPet();
        changedPet = PetFabric.updatedPet();
    }

    @Test
    @DisplayName("Добавление нового питомца")
    public void addNewPetTest() {
        PetSteps.addPet(pet);
    }

    @Test
    @DisplayName("Поиск и сравнение питомца созданного в первом тесте")
    public void findPetByIdTest() {
        PetSteps.addPet(pet);
        Pet receivedPet = PetSteps.findPet(pet);
        assertThat(receivedPet)
                .isEqualTo(pet);
    }

    @Test
    @DisplayName("Обновление существующего питомца")
    public void updatePetTest() {
        PetSteps.addPet(pet);
        Pet receivedPet = PetSteps.updatingPet(changedPet);
        assertThat(receivedPet)
                .isEqualTo(changedPet);
    }

    @Disabled
    @Flaky
    @Test
    @DisplayName("Обновление питомца в магазине через форму")
    public void updatePetNameAndStatusWithFormDataTest() {
        String newName = "changed";
        Status newStatus = Status.SOLD;
        PetSteps.addPet(pet);
        PetSteps.updatesPetNameAndStatusWithFormData(pet, newName, newStatus);
        Pet recievedPet = PetSteps.findPet(pet);
        softAssertions.assertThat(recievedPet.getName())
                      .isEqualTo(newName);
        softAssertions.assertThat(recievedPet.getStatus())
                      .isEqualTo(newStatus.getStatus());
        softAssertions.assertAll();
    }

    @Test
    @DisplayName("Загрузить изображение питомца по id")
    public void uploadAnImageTest() {
        PetSteps.uploadAnImageTest(pet.getId(), imagePath);
    }

    @Test
    @DisplayName("Удаление созданного питомца")
    public void deletePetTest() {
        PetSteps.addPet(pet);
        PetSteps.deletePet(pet);
    }


    @Test
    @DisplayName("Поиск несуществующего питомца")
    public void nonExistPetTest() {
        PetSteps.findNonExistPetTest(PetFabric.nonExistingPet());
    }

    @ParameterizedTest
    @EnumSource(Status.class)
    @DisplayName("Поиск питомца по статусу, проверка на соответсвтие статуса питомцев искомому")
    public void findPetsByStatusTest(Status status) {
        Pet[] pets = PetSteps.findPetsByStatus(status);
        assertThat(pets)
                .allMatch(pet -> pet.getStatus().equals(status.getStatus()));
    }

    @Test
    @DisplayName("Использование некорректного body при добавлении питомца")
    public void addPetWrongBodyTest() {
        PetSteps.addPetWrongBody();
    }

    @Test
    public void addPetWrongHttpMethodTest() {
        PetSteps.addPetWrongHttpMethod();
    }

}
