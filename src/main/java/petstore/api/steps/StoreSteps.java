package petstore.api.steps;

import com.google.gson.Gson;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.awaitility.Awaitility;
import petstore.api.dto.adapters_gson.GsonProvider;
import petstore.api.dto.store.StoreOrder;
import petstore.api.endpoints.StoreEndPoints;
import petstore.api.props.PropsHelper;

import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;
import static petstore.api.spec.Specifications.*;

public class StoreSteps {
    static PropsHelper propsHelper = new PropsHelper();

    public static final String baseUrl = propsHelper.getProperty("baseUrl");
    public static final Long responseTime = 10000L;

    static Gson gson = GsonProvider.getGson();

    @Step("Получение заказа")
    public static StoreOrder getStoreOrder(StoreOrder order) {
        installSpecification(requestSpec(baseUrl), responseSpecOK200(responseTime));

        final StoreOrder[] receivedStoreOrderHolder = new StoreOrder[1];
        Awaitility.await()
                .atMost(10, TimeUnit.SECONDS)
                .pollDelay(1000, TimeUnit.MILLISECONDS)
                .pollInterval(1000, TimeUnit.MILLISECONDS)
                .until(() -> {
                    Response response = given().when()
                            .get(StoreEndPoints.ORDER, order.getId());

                    StoreOrder receivedStoreOrder = gson.fromJson(response.getBody()
                            .asString(), StoreOrder.class);
                    receivedStoreOrderHolder[0] = receivedStoreOrder;
                    return receivedStoreOrderHolder[0].equals(order);
                });

        return receivedStoreOrderHolder[0];
    }

    @Step("Размещение заказа")
    public static void placeStoreOrder(StoreOrder order) {
        installSpecification(requestSpec(baseUrl), responseSpecOK200(responseTime));
        given().body(gson.toJson(order))
                .when()
                .post(StoreEndPoints.PLACE_ORDER);
    }

    @Step("Удаление заказа")
    public static void deleteStoreOrder(StoreOrder order) {
        installSpecification(requestSpec(baseUrl), responseSpecOK200(responseTime));
        given().when()
                .delete(StoreEndPoints.DELETE_STORE_ORDER, order.getId());
    }

}

