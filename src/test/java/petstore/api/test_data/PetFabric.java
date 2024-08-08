package petstore.api.test_data;

import petstore.api.dto.pet.Category;
import petstore.api.dto.pet.Pet;
import petstore.api.dto.pet.Status;
import petstore.api.dto.pet.TagsItem;

import java.util.List;

public abstract class PetFabric {

    public static Pet defaultPet() {
        // ToDo перенести
        Category hedgehog = Category.builder()
                .setId(7)
                .setName("Ежи")
                .build();

        // ToDo перенести
        TagsItem thorny = TagsItem.builder()
                .setId(2)
                .setName("Колючие")
                .build();

        List<TagsItem> tags = List.of(thorny);
        List<String> urls = List.of("https://some-url.com");

        return Pet.builder()
                  .setId(254)
                .setCategory(hedgehog)
                .setName("Курва Ежик")
                .setPhotoUrls(urls)
                .setTags(tags)
                .setStatus(Status.AVAILABLE.getStatus())
                .build();
    }

    public static Pet updatedPet() {
        // ToDo перенести
        Category hedgehog = Category.builder()
                                    .setId(8)
                                    .setName("Ежи changed")
                                    .build();

        // ToDo перенести
        TagsItem thorny = TagsItem.builder()
                                  .setId(3)
                                  .setName("Колючие changed")
                                  .build();

        List<TagsItem> tags = List.of(thorny);
        List<String> urls = List.of("https://some-url.com/changed");

        return Pet.builder()
                  .setId(254)
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
