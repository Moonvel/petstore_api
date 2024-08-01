package petstore.api.dto.pet;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Pet {

  private int id;
  private Category category;
  private String name;
  private List<String> photoUrls;
  private List<TagsItem> tags;
  private String status;

  public static Pet defaultPet() {
    Category hedgehog = Category.builder().id(7).name("Ежи").build();
    TagsItem thorny = TagsItem.builder().id(2).name("Колючие").build();
    List<TagsItem> tags = new ArrayList<>();
    tags.add(thorny);
    return Pet
        .builder()
        .id(111)
        .category(hedgehog)
        .name("Курва Ежик")
        .tags(tags)
        .status("available")
        .build();
  }
}