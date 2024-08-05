package petstore.api.dto.pet;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder(setterPrefix = "set")
@Data
public class Pet {
    private long id;
    private Category category;
    private String name;
    private List<String> photoUrls;
    private List<TagsItem> tags;
    private String status;
}
