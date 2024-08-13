package petstore.api.test_data;

import petstore.api.dto.pet.Category;
import petstore.api.dto.pet.Pet;
import petstore.api.dto.pet.Status;
import petstore.api.dto.pet.TagsItem;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public abstract class PetFabric {
    private static Long petId = 254L;

    public static Pet defaultPet() {
        Category hedgehog = Category.builder()
                .setId(8)
                .setName("Ежи")
                .build();

        TagsItem thorny = TagsItem.builder()
                .setId(2)
                .setName("Колючие")
                .build();

        List<TagsItem> tags = List.of(thorny);
        List<String> urls = List.of("https://some-url.com");

        return Pet.builder()
                .setId(petId++)
                .setCategory(hedgehog)
                .setName("Курва Ежик")
                .setPhotoUrls(urls)
                .setTags(tags)
                .setStatus(Status.AVAILABLE.getStatus())
                .build();
    }

    public static Pet updatedPet() {
        Category hedgehog = Category.builder()
                                    .setId(8)
                                    .setName("Ежи changed")
                                    .build();

        TagsItem thorny = TagsItem.builder()
                                  .setId(3)
                                  .setName("Колючие changed")
                                  .build();

        List<TagsItem> tags = List.of(thorny);
        List<String> urls = List.of("https://some-url.com/changed");

        return Pet.builder()
                  .setId(petId++)
                  .setCategory(hedgehog)
                  .setName("Курва changed")
                  .setPhotoUrls(urls)
                  .setTags(tags)
                  .setStatus(Status.SOLD.getStatus())
                  .build();
    }

    public static Pet nonExistingPet() {
        return Pet.builder()
                  .setId(445646654654646L)
                  .build();
    }


}
