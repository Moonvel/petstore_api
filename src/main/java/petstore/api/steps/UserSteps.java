package petstore.api.steps;

import io.qameta.allure.Step;
import org.awaitility.Awaitility;
import petstore.api.dto.user.User;
import petstore.api.endpoints.PetEndPoints;
import petstore.api.endpoints.UserEndPoints;
import petstore.api.props.PropsHelper;
import petstore.api.spec.Specifications;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;
import static petstore.api.spec.Specifications.installSpecification;
import static petstore.api.spec.Specifications.requestSpec;
import static petstore.api.spec.Specifications.requestSpecUrlenc;
import static petstore.api.spec.Specifications.responseSpecOK200;

public abstract class UserSteps {
	static PropsHelper propsHelper = new PropsHelper();

	public static final String baseUrl = propsHelper.getProperty("baseUrl");
	public static final Long responseTime = 10000L;

	@Step("Создание пользователя")
	public static void createUser(User user) {
		Specifications.installSpecification(requestSpec(baseUrl), responseSpecOK200(responseTime));
		given()
				.body(user)
				.when()
				.post(UserEndPoints.CREATE_USER);
	}


	@Step("Поиск пользователя по username")
	public static User getUserByUserName(User user) {
		Specifications.installSpecification(requestSpec(baseUrl), responseSpecOK200(responseTime));

		final User[] receivedUserHolder = new User[1];
		Awaitility.await()
				  .atMost(10, TimeUnit.SECONDS)
				  .pollDelay(2000, TimeUnit.MILLISECONDS)
				  .pollInterval(1000, TimeUnit.MILLISECONDS)
				  .until(() -> {
					  User recievedUser = given()
							  .when()
							  .get(UserEndPoints.GET_USER_BY_USERNAME, user.getUsername())
							  .as(User.class);
					  receivedUserHolder[0] = recievedUser;
					  return recievedUser.equals(user);
				  });
		return receivedUserHolder[0];
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
	public static User updateUser(User user) {
		Specifications.installSpecification(requestSpec(baseUrl), responseSpecOK200(responseTime));
		given()
				.body(user)
				.when()
				.put(UserEndPoints.UPDATE_USER, user.getUsername());

		return UserSteps.getUserByUserName(user);

	}


	@Step("Удаление пользователя")
	public static void deleteUser(String username) {
		installSpecification(requestSpec(baseUrl), responseSpecOK200(responseTime));
		Awaitility.await()
				  .atMost(10, TimeUnit.SECONDS)
				  .pollDelay(2000, TimeUnit.MILLISECONDS)
				  .pollInterval(1000, TimeUnit.MILLISECONDS)
				  .until(() -> given()
						  .when()
						  .delete(UserEndPoints.DELETE_USER, username)
						  .getStatusCode() == 200);
	}

	@Step("Login")
	public static void login(String userName, String passWord) {
		installSpecification(requestSpecUrlenc(baseUrl), responseSpecOK200(responseTime));
		given().formParam("username", userName)
			   .formParam("password", passWord)
			   .when()
			   .get(UserEndPoints.LOGIN);
	}

	@Step("Logout")
	public static void logout() {
		installSpecification(requestSpec(baseUrl), responseSpecOK200(responseTime));
		given().when()
			   .get(UserEndPoints.LOGOUT);
	}
}
