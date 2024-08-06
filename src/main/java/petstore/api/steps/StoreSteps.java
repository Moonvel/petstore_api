package petstore.api.steps;

import com.google.gson.Gson;
import io.restassured.response.Response;
import petstore.api.dto.adapters_gson.GsonProvider;
import petstore.api.dto.store.StoreOrder;
import petstore.api.endpoints.StoreEndPoints;
import petstore.api.props.PropsHelper;

import static io.restassured.RestAssured.given;
import static petstore.api.spec.Specifications.installSpecification;
import static petstore.api.spec.Specifications.requestSpec;
import static petstore.api.spec.Specifications.responseSpecOK200;

public class StoreSteps {
	static PropsHelper propsHelper = new PropsHelper();

	public static final String baseUrl = propsHelper.getProperty("baseUrl");
	public static final Long responseTime = 10000L;

	static Gson gson = GsonProvider.getGson();


	public static StoreOrder getStoreOrder(int orderId) {
		installSpecification(requestSpec(baseUrl), responseSpecOK200(responseTime));
		Response response = given().when()
								   .get(StoreEndPoints.ORDER, orderId);

		return gson.fromJson(response.getBody()
									 .asString(), StoreOrder.class);
	}

	public static void placeOrder(StoreOrder order) {
		installSpecification(requestSpec(baseUrl), responseSpecOK200(responseTime));
		given().body(gson.toJson(order))
			   .when()
			   .post(StoreEndPoints.PLACE_ORDER);
	}

}

