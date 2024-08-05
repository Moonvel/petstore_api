package petstore.api.props;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropsHelper {
	public static final Properties properties = new Properties();
	private static PropsHelper instance;

	public PropsHelper() {
		try (FileInputStream fileInputStream = new FileInputStream("src/test/resources/project.properties")) {
			properties.load(fileInputStream);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static PropsHelper getInstance() {
		if (instance == null) {
			instance = new PropsHelper();
		}
		return instance;
	}

	public String getProperty(String key) {
		return properties.getProperty(key);
	}
}

