package petstore.api.spec;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static org.hamcrest.Matchers.lessThanOrEqualTo;

public class Specifications {

    // region RequestSpecification
    private static RequestSpecBuilder getDefaultSpec(String url) {
        return new RequestSpecBuilder()
                .setAccept(ContentType.JSON)
                .setBaseUri(url);
    }

    public static RequestSpecification requestSpec(String url) {
        return getDefaultSpec(url)
                .setContentType(ContentType.JSON)
                .build();
    }

    public static RequestSpecification requestSpecMultiPart(String url) {
        return getDefaultSpec(url)
                .setContentType(ContentType.MULTIPART)
                .build();
    }

    public static RequestSpecification requestSpecUrlenc(String url) {
        return getDefaultSpec(url)
                .setContentType(ContentType.URLENC)
                .build();
    }
    // endregion

    // region ResponseSpecification
    public static ResponseSpecification responseSpecOK200(Long responseTime) {
        return new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectResponseTime(lessThanOrEqualTo(responseTime))
                .build();
    }

    public static ResponseSpecification responseSpecError(int errorCode) {
        return new ResponseSpecBuilder()
                .expectStatusCode(errorCode)
                .build();
    }
    // endregion

    public static void installSpecification(RequestSpecification request, ResponseSpecification response
    ) {
        RestAssured.requestSpecification = request;
        RestAssured.responseSpecification = response;
    }

    public static void installSpecification(RequestSpecification request) {
        RestAssured.requestSpecification = request;
    }
}
