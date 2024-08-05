package petstore.api.dto.pet;

import lombok.Builder;
import lombok.Getter;

@Builder(setterPrefix = "set")
@Getter
public class Category {
    private long id;
    private String name;
}
