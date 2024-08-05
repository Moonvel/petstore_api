package petstore.api.dto.pet;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder(setterPrefix = "set")
@Getter
@Setter
public class Pet {
    private long id;
    private Category category;
    private String name;
    private List<String> photoUrls;
    private List<TagsItem> tags;
    private String status;
}
