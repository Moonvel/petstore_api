package petstore.api.steps;

import io.restassured.response.Response;
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

	public static Response getStoreOrder(int orderId) {
		installSpecification(requestSpec(baseUrl), responseSpecOK200(responseTime));
		return given().when()
			   .get(StoreEndPoints.ORDER, orderId);
	}
}
