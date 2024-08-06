package petstore.api.dto.store;

import lombok.Builder;
import lombok.Data;

@Builder(setterPrefix = "set")
@Data
public class StoreOrder {
	private int petId;
	private int quantity;
	private int id;
	private String shipDate;
	private boolean complete;
	private String status;
}