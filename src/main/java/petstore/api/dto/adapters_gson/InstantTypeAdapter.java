package petstore.api.dto.adapters_gson;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

public class InstantTypeAdapter extends TypeAdapter<Instant> {
	private static final DateTimeFormatter formatter = new DateTimeFormatterBuilder()
			.appendPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")
			.optionalStart()
			.appendOffset("+HHmm", "Z")
			.optionalEnd()
			.toFormatter();

	@Override
	public void write(JsonWriter out, Instant value) throws IOException {
		out.value(formatter.format(ZonedDateTime.ofInstant(value, java.time.ZoneOffset.UTC)));
	}

	@Override
	public Instant read(JsonReader in) throws IOException {
		String date = in.nextString();
		return ZonedDateTime.parse(date, formatter)
							.toInstant();
	}
}