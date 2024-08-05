package petstore.api.steps;

import io.qameta.allure.Step;
import petstore.api.dto.user.User;
import petstore.api.endpoints.UserEndPoints;
import petstore.api.props.PropsHelper;
import petstore.api.spec.Specifications;

import java.util.List;

import static io.restassured.RestAssured.given;
import static petstore.api.spec.Specifications.requestSpec;
import static petstore.api.spec.Specifications.responseSpecOK200;

public abstract class UserSteps {
	static PropsHelper propsHelper = new PropsHelper();

	public static final String baseUrl = propsHelper.getProperty("baseUrl");
	public static final Long responseTime = 4000L;

	@Step("Создание пользователя")
	public static void createUser(User user) {
		Specifications.installSpecification(requestSpec(baseUrl), responseSpecOK200(responseTime));
		given()
				.body(user)
				.when()
				.post(UserEndPoints.CREATE_USER);
	}

	@Step("Поиск пользователя по username")
	public static User getUserByUserName(String userName) {
		Specifications.installSpecification(requestSpec(baseUrl), responseSpecOK200(responseTime));
		return given()
				.when()
				.get(UserEndPoints.GET_USER_BY_USERNAME, userName)
				.as(User.class);
	}

	@Step("Создание списка пользователей")
	public static void createUsersWithList(List<User> users) {
		Specifications.installSpecification(requestSpec(baseUrl), responseSpecOK200(responseTime));
		given()
				.body(users)
				.when()
				.post(UserEndPoints.CREATE_USERS_WITH_LIST);
	}

	@Step("Обновление пользователя")
	public static void updateUser(User user) {
		Specifications.installSpecification(requestSpec(baseUrl), responseSpecOK200(responseTime));
		given()
				.body(user)
				.when()
				.put(UserEndPoints.UPDATE_USER, user.getUsername());
	}
}
