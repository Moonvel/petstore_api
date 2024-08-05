package petstore.api.dto.pet;

import lombok.Builder;
import lombok.Data;

@Builder(setterPrefix = "set")
@Data
public class Category {
    private long id;
    private String name;
}
