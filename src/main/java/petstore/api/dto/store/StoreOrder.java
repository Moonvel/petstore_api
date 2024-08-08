package petstore.api.dto.store;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Builder(setterPrefix = "set")
@Data
public class StoreOrder {
	private int id;
	private int petId;
	private int quantity;
	private Instant shipDate;
	private boolean complete;
	private String status;
}