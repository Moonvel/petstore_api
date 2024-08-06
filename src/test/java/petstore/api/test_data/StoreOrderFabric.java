package petstore.api.test_data;

import petstore.api.dto.store.StoreOrder;

import java.time.ZonedDateTime;

public abstract class StoreOrderFabric {

	public static StoreOrder defaultStoreOrder() {
		return StoreOrder.builder()
						 .setId(5)
						 .setPetId(112)
						 .setQuantity(2)
						 .setShipDate(ZonedDateTime.now())
						 .setComplete(true)
						 .setStatus("test")
						 .build();
	}
}
