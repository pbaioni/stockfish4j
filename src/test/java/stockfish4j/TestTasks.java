package stockfish4j;

import static org.junit.Assert.assertTrue;

import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import stockfish4j.model.EngineEvaluation;
import stockfish4j.service.Stockfish4jProperties;
import stockfish4j.service.StockfishService;
import stockfish4j.utils.FenUtils;

public class TestTasks {

	private static Stockfish4jProperties props;
	private static StockfishService service;

	@BeforeClass
	public static void init() {
		props = Stockfish4jProperties.getInstance();
		props.setStockfishInstances(1);
		service = new StockfishService(props);
	}
	
	@AfterClass
	public static void destroy() {
		service.destroy();
	}
	
	@Test
	public void TestDepthTask() throws InterruptedException, ExecutionException {
		EngineEvaluation eval = service.submitDepthTask(FenUtils.getRandomFen(), 20).get();
		assertTrue(!Objects.isNull(eval.getBestmove()));
	}
	
	@Test
	public void TestMovetimeTask() throws InterruptedException, ExecutionException {

		EngineEvaluation eval = service.submitTimeTask(FenUtils.getRandomFen(), 3000).get();
		assertTrue(!Objects.isNull(eval.getBestmove()));
	}
	
	@Test
	public void TestNodesTask() throws InterruptedException, ExecutionException {
		
		EngineEvaluation eval = service.submitNodesTask(FenUtils.getRandomFen(), 1000000).get();
		assertTrue(!Objects.isNull(eval.getBestmove()));
	}
	
	@Test
	public void TestMateTask() throws InterruptedException, ExecutionException {
		String fen = FenUtils.getRandomFen();
		Future<EngineEvaluation> eval = service.submitMateTask(fen, 1);
		Thread.sleep(3000);
		service.cancelTask(fen);
		assertTrue(!Objects.isNull(eval.get().getBestmove()));
	}
	
	@Test
	public void TestInfiniteTask() throws InterruptedException, ExecutionException {
		String fen = FenUtils.getRandomFen();
		Future<EngineEvaluation> eval = service.submitInfiniteTask(fen);
		Thread.sleep(5000);
		service.cancelTask(fen);
		assertTrue(!Objects.isNull(eval.get().getBestmove()));
	}

}
