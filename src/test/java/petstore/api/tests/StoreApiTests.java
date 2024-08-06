package petstore.api.tests;

import jdk.jfr.Description;
import org.junit.jupiter.api.Test;
import petstore.api.dto.store.StoreOrder;
import petstore.api.steps.StoreSteps;

public class StoreApiTests {
	@Test
	@Description("Получение инвентаря магазина")
	public void getStoreOrderTest() {
		StoreOrder store = StoreSteps.getStoreOrder(1);
	}

}
