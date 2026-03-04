package stockfish4j.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;
import java.util.logging.Logger;

public class Stockfish4jProperties {

	private static final Logger LOGGER = Logger.getLogger("Stockfish4j");

	private static Stockfish4jProperties instance;

	private Properties propertiesMap;

	private String STOCKFISH4J_PROPERTIES_FILE = "stockfish4j.properties";

	private String enginePath = "enginePath";

	private String stockfishInstances = "stockfishInstancies";

	private String timeout = "timeout";
	
	private String engineVerbosity = "engineVerbosity";

	public Stockfish4jProperties() {

		try (InputStream input = Stockfish4jProperties.class.getClassLoader()
				.getResourceAsStream(STOCKFISH4J_PROPERTIES_FILE)) {

			propertiesMap = new Properties();

			if (input == null) {
				LOGGER.info("Sorry, unable to find file stockfish4j.properties");
				return;
			}

			propertiesMap.load(input);

		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}

	public static Stockfish4jProperties getInstance() {

		if (Objects.isNull(instance)) {
			instance = new Stockfish4jProperties();
		}
		return instance;

	}

	public String getEnginePath() {

		return propertiesMap.getProperty(enginePath, "./engine/stockfish");

	}

	public void setEnginePath(String enginePath) {
		propertiesMap.setProperty(this.enginePath, enginePath);
	}

	public Integer getStockfishInstances() {

		return Integer.parseInt(propertiesMap.getProperty(stockfishInstances, "1"));

	}
	
	public void setStockfishInstances(int stockfishInstances) {
		propertiesMap.setProperty(this.stockfishInstances, Integer.toString(stockfishInstances));
	}

	public int getTimeout() {
		return Integer.parseInt(propertiesMap.getProperty(timeout, "30"));
	}

	public void setTimeout(int timeout) {
		propertiesMap.setProperty(this.timeout, Integer.toString(timeout));
	}
	
	public int getEngineVerbosity() {
		return Integer.parseInt(propertiesMap.getProperty(engineVerbosity, "1"));
	}

	public void setEngineVerbosity(int engineVerbosity) {
		propertiesMap.setProperty(this.engineVerbosity, Integer.toString(engineVerbosity));
	}

	@Override
	public String toString() {
		return "Stockfish4jProperties{" +
				"enginePath='" + getEnginePath() + '\'' +
				", timeout='" + getTimeout() + '\'' +
				", stockfishInstances='" + getStockfishInstances() + '\'' +
				", engineVerbosity='" + getEngineVerbosity() + '\'' +
				'}';
	}
}
