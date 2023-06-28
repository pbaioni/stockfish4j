package stockfish4j.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.logging.Logger;

public class Stockfish4jProperties {

	private static final Logger LOGGER = Logger.getLogger("Stockfish4j");

	private static Stockfish4jProperties instance;

	private Properties stockfish4jProperties;

	private String STOCKFISH4J_PROPERTIES_FILE = "stockfish4j.properties";

	private String enginePath = "enginePath";

	private String stockfishInstances = "stockfishInstances";

	private String timeout = "timeout";
	
	private String engineVerbosity = "engineVerbosity";

	private Stockfish4jProperties() {

		try (InputStream input = Stockfish4jProperties.class.getClassLoader()
				.getResourceAsStream(STOCKFISH4J_PROPERTIES_FILE)) {

			stockfish4jProperties = new Properties();

			if (input == null) {
				LOGGER.info("Sorry, unable to find file stockfish4j.properties");
				return;
			}

			stockfish4jProperties.load(input);

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

		return stockfish4jProperties.getProperty(enginePath, "./engine/stockfish");

	}

	public void setEnginePath(String enginePath) {
		stockfish4jProperties.setProperty(enginePath, enginePath);
	}

	public Integer getStockfishInstances() {

		return Integer.parseInt(stockfish4jProperties.getProperty(stockfishInstances, "1"));

	}
	
	public void setStockfishInstances(int stockfishInstances) {
		stockfish4jProperties.setProperty(this.stockfishInstances, Integer.toString(stockfishInstances));
	}

	public int getTimeout() {
		return Integer.parseInt(stockfish4jProperties.getProperty(timeout, "30"));
	}

	public void setTimeout(int timeout) {
		stockfish4jProperties.setProperty(this.timeout, Integer.toString(timeout));
	}
	
	public int getEngineVerbosity() {
		return Integer.parseInt(stockfish4jProperties.getProperty(engineVerbosity, "1"));
	}

	public void setEngineVerbosity(int engineVerbosity) {
		stockfish4jProperties.setProperty(this.engineVerbosity, Integer.toString(engineVerbosity));
	}
}
