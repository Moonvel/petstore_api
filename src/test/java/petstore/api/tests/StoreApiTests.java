package petstore.api.tests;

import jdk.jfr.Description;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import petstore.api.dto.store.StoreOrder;
import petstore.api.steps.StoreSteps;
import petstore.api.test_data.StoreOrderFabric;

import static org.assertj.core.api.Assertions.assertThat;
import static petstore.api.test_data.StoreOrderFabric.defaultStoreOrder;

public class StoreApiTests {
	static StoreOrder order;
	@BeforeEach
	public void setUp() {
		order = StoreOrderFabric.defaultStoreOrder();
	}

	@Test
	@Description("Получение заказа")
	public void getStoreOrderTest() {
		StoreSteps.placeStoreOrder(order);
		StoreOrder receivedOrder = StoreSteps.getStoreOrder(order);
		assertThat(receivedOrder)
				.isEqualTo(order);
	}

	@Test
	@Description("Размещение заказа")
	public void placeOrderTest() {
		StoreSteps.placeStoreOrder(order);
	}

	@Test
	@Description("Удаление заказа")
	public void deleteStoreOrder() {
		StoreSteps.placeStoreOrder(order);
		StoreSteps.deleteStoreOrder(order);
	}

}
