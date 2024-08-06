package petstore.api.tests;

import jdk.jfr.Description;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import petstore.api.dto.store.StoreOrder;
import petstore.api.steps.StoreSteps;
import petstore.api.test_data.StoreOrderFabric;

import static org.assertj.core.api.Assertions.assertThat;
import static petstore.api.test_data.StoreOrderFabric.defaultStoreOrder;

public class StoreApiTests {

	@Test
	@Description("Получение заказа")
	public void getStoreOrderTest() {
		StoreOrder store = StoreSteps.getStoreOrder(defaultStoreOrder().getId());
	}

	@Disabled
	@Test
	@Description("Размещение заказа")
	public void placeOrderTest() {
		StoreOrder order = defaultStoreOrder();
		StoreSteps.placeOrder(order);
		StoreOrder receivedOrder = StoreSteps.getStoreOrder(order.getId());
		assertThat(receivedOrder)
				.isEqualTo(order);
	}

}
