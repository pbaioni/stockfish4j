package stockfish4j;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.logging.Logger;

import stockfish4j.model.EngineEvaluation;
import stockfish4j.service.Stockfish4jProperties;
import stockfish4j.service.StockfishService;

public class Stockfish4j {

	private static final Logger LOGGER = Logger.getLogger("Stockfish4j");

	public static void main(String[] args) throws InterruptedException, ExecutionException {

		LOGGER.info("Stockfish4j started");

		Stockfish4jProperties props = Stockfish4jProperties.getInstance();

		props.setStockfishInstances(4);
		StockfishService service = new StockfishService(props);
		service.setEngineOption("Threads", Integer.toString(1));
		Future<EngineEvaluation> timeTask = service
				.submitTimeTask("r4rk1/1pp1qppp/p1np1n2/2b1p1B1/2B1P1b1/P1NP1N2/1PP1QPPP/R4RK1 w - - 0 10", 5000);

		Future<EngineEvaluation> depthTask = service
				.submitDepthTask("r4rk1/1pp1qppp/p1np1n2/2b1p1B1/2B1P1b1/P1NP1N2/1PP1QPPP/R4RK1 w - - 0 10", 30);

		Thread.sleep(2000);
		depthTask.cancel(false);
		LOGGER.info("Stockfish4j stopped");

	}

}
