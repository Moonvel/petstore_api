package petstore.api.dto.adapters_gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.time.Instant;

public class GsonProvider {
	public static Gson getGson() {
		return new GsonBuilder()
				.registerTypeAdapter(Instant.class, new InstantTypeAdapter())
				.create();
	}
}