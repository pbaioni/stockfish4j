package stockfish4j.model;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.logging.Logger;

public class EngineOptions {

	private static final Logger LOGGER = Logger.getLogger("Stockfish4j");

	private static EngineOptions instance;

	Properties engineProperties;

	private String STOCKFISH_PROPERTIES_FILE = "engine.properties";

	private EngineOptions() {

		try (InputStream input = EngineOptions.class.getClassLoader()
				.getResourceAsStream(STOCKFISH_PROPERTIES_FILE)) {

			engineProperties = new Properties();

			if (input == null) {
				LOGGER.info("Sorry, unable to find file engine.properties");
				return;
			}

			engineProperties.load(input);
			List<String> voidKeys = new ArrayList<String>();
			for(Object key : engineProperties.keySet()) {
				if(engineProperties.get(key).equals("")) {
					voidKeys.add((String)key);
				}
			}
			for(String key : voidKeys) {
				engineProperties.remove(key);
			}
			System.out.println("Engine options: " + engineProperties);

		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}

	public static EngineOptions getInstance() {

		if (Objects.isNull(instance)) {
			instance = new EngineOptions();
		}
		return instance;

	}
	
	public Properties getPropertiesTable() {
		return engineProperties;
	}
	
	public String getOption(String key) {
		return engineProperties.getProperty(key);
	}
	
	public void setOption(String key, String value) {
		engineProperties.setProperty(key, value);
	}

}
