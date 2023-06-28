package stockfish4j.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Logger;

import stockfish4j.model.EngineEvaluation;
import stockfish4j.model.EvaluationType;
import stockfish4j.model.StockfishEngine;

public class StockfishService {

	private static final Logger LOGGER = Logger.getLogger("Stockfish4j");

	private Stockfish4jProperties stockfish4jProperties;

	private static ArrayBlockingQueue<StockfishEngine> engines;

	private ExecutorService executorService;

	private List<EngineTask> tasks;

	private List<EngineTask> done;

	public StockfishService(Stockfish4jProperties props) {
		
		this.stockfish4jProperties = props;
		executorService = Executors.newFixedThreadPool(stockfish4jProperties.getStockfishInstances());
		engines = new ArrayBlockingQueue<StockfishEngine>(stockfish4jProperties.getStockfishInstances());
		tasks = new ArrayList<EngineTask>();
		done = new ArrayList<>();
		
		for (int i = 1; i <= stockfish4jProperties.getStockfishInstances(); i++) {
			StockfishEngine engine = new StockfishEngine("Engine" + i, stockfish4jProperties.getEnginePath(),
					stockfish4jProperties.getEngineVerbosity());
			engines.add(engine);
		}

		LOGGER.info("A service running " + stockfish4jProperties.getStockfishInstances()
				+ " stockfish instancies has been created");
		
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new PruneTask(this), 0, 1000);

	}

	public void setEngineOption(String optionName, String value) {
		for (StockfishEngine engine : engines) {
			engine.setOption(optionName, value);
		}
	}

	public Future<EngineEvaluation> submitDepthTask(String fen, int depth) {

		return executeTask(new EngineTask(engines, fen, EvaluationType.DEPTH, depth));
	}

	public Future<EngineEvaluation> submitTimeTask(String fen, int time) {
		return executeTask(new EngineTask(engines, fen, EvaluationType.MOVETIME, time));
	}

	public Future<EngineEvaluation> submitNodesTask(String fen, int nodes) {
		return executeTask(new EngineTask(engines, fen, EvaluationType.NODES, nodes));
	}

	public Future<EngineEvaluation> submitMateTask(String fen, int plies) {
		return executeTask(new EngineTask(engines, fen, EvaluationType.MATE, plies));
	}

	public Future<EngineEvaluation> submitInfiniteTask(String fen) {
		return executeTask(new EngineTask(engines, fen, EvaluationType.INFINITE, 0));
	}
	
	public void cancelTask(String fen) throws InterruptedException {

		System.out.println("Cancelling analyse for fen " + fen);
		for (EngineTask task : tasks) {
			if (task.getFen().equals(fen)) {
				task.stop();
			}
		}
	}

	public void cancelAll() throws InterruptedException {

		System.out.println("Cancelling " + tasks.size() + " pending tasks... ");
		for (EngineTask task : tasks) {
			task.stop();
		}

		while (!tasks.isEmpty()) {
			pruneRunningTasks();
			Thread.sleep(100);
		}

		System.out.println("Tasks cancelled");
	}
	
	public synchronized void pruneRunningTasks() {
		
		for (EngineTask task : tasks) {
			if (task.isDone()) {
				done.add(task);
			}
		}

		for (EngineTask complete : done) {
			tasks.remove(complete);
		}

		done.clear();
	}

	public void destroy() {

		for (StockfishEngine engine : engines) {
			engine.quit();
		}

		executorService.shutdown();

		LOGGER.info("Stockfish service destroyed");
	}

	private Future<EngineEvaluation> executeTask(EngineTask task) {

		tasks.add(task);

		return executorService.submit(task);
	}
}
