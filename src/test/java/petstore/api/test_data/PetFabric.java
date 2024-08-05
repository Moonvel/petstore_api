package petstore.api.test_data;

import petstore.api.dto.pet.Category;
import petstore.api.dto.pet.Pet;
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
                .setId(111)
                .setCategory(hedgehog)
                .setName("Курва Ежик")
                .setPhotoUrls(urls)
                .setTags(tags)
                .setStatus("available")
                .build();
    }
}
