package petstore.api.dto.pet;

import lombok.Builder;
import lombok.Data;


@Builder(setterPrefix = "set")
@Data
public class TagsItem {
    private int id;
    private String name;
}
