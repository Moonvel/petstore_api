package petstore.api.tools;

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
                .setBaseUri(url);
    }

    // ToDo рефакторинг
    public static RequestSpecification requestSpec(String url) {
        return getDefaultSpec(url)
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .build();
    }

    public static RequestSpecification requestSpecMultiPart(String url) {
        return new RequestSpecBuilder()
                .setBaseUri(url)
                .setContentType(ContentType.MULTIPART)
                .setAccept(ContentType.JSON)
                .build();
    }

    public static RequestSpecification requestSpecUrlenc(String url) {
        return new RequestSpecBuilder()
                .setBaseUri(url)
                .setContentType(ContentType.URLENC)
                .setAccept(ContentType.JSON)
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

    public static void installSpecification(
            RequestSpecification request,
            ResponseSpecification response
    ) {
        RestAssured.requestSpecification = request;
        RestAssured.responseSpecification = response;
    }
}
