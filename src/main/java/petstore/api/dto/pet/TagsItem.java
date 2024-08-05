package petstore.api.dto.pet;

import lombok.Builder;
import lombok.Getter;


@Builder(setterPrefix = "set")
@Getter
public class TagsItem {
    private int id;
    private String name;
}
