package petstore.api.dto.adapters_gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.time.ZonedDateTime;

public class GsonProvider {
	public static Gson getGson() {
		return new GsonBuilder()
				.registerTypeAdapter(ZonedDateTime.class, new ZoneDateTimeAdapter())
				.create();
	}
}
