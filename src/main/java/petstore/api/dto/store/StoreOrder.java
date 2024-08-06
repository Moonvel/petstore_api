package petstore.api.dto.store;

import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;

@Builder(setterPrefix = "set")
@Data
public class StoreOrder {
	private int petId;
	private int quantity;
	private int id;
	private ZonedDateTime shipDate;
	private boolean complete;
	private String status;
}