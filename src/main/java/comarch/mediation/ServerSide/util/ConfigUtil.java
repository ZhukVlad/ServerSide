package comarch.mediation.ServerSide.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ConfigUtil {
	private static final String PROPERTIES_PATH = "src/main/resources/";
	private static final String PROPERTIES_EXT = ".properties";
	private static final String CANNOT_LOAD_DATA = "Cannot load data ";
	
	public static final String MEDIATION_FILE = "mediation";
	public static final String RATE = "rate";
	public static final String CACHE_SIZE = "cacheSize";
	
	public static Long id = 1L;
	public static String clientHost;
	public static Boolean isClientRegistered = Boolean.FALSE;
	
	private Properties properties;
	
	public ConfigUtil() {
	}
	
	public Map<String, Object> getConfig(String fileName) {
		Map<String, Object> config = null;
		try (InputStream inputStream = new FileInputStream(new File(PROPERTIES_PATH + fileName + PROPERTIES_EXT))) {
			if (inputStream != null) {
				properties = new Properties();
				config = new HashMap<>();
				properties.load(inputStream);
				for (String name : properties.stringPropertyNames()) {
					config.put(name, properties.getProperty(name));
				}
			}
		} catch (IOException e) {
			LoggerUtil.error(CANNOT_LOAD_DATA, e);
		}
		return config;
	}
}
