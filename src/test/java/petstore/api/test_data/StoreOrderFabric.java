package petstore.api.test_data;

import petstore.api.dto.store.StoreOrder;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public abstract class StoreOrderFabric {

	public static StoreOrder defaultStoreOrder() {
		return StoreOrder.builder()
						 .setId(6)
						 .setPetId(112)
						 .setQuantity(2)
						 .setShipDate(Instant.now().truncatedTo(ChronoUnit.MILLIS))
						 .setComplete(true)
						 .setStatus("test")
						 .build();
	}
}
