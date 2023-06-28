package stockfish4j;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.junit.Test;

import stockfish4j.model.EngineEvaluation;
import stockfish4j.service.Stockfish4jProperties;
import stockfish4j.service.StockfishService;
import stockfish4j.utils.FenUtils;

public class TestClient {



	List<Future<EngineEvaluation>> pending = new LinkedList<Future<EngineEvaluation>>();

	private void createTasks(StockfishService service) {

		for (String fen : FenUtils.getFens()) {
			pending.add(service.submitDepthTask(fen, 21));
		}
	}

	@Test
	public void ClientTest() throws InterruptedException, ExecutionException {

		Stockfish4jProperties props = Stockfish4jProperties.getInstance();

		props.setStockfishInstances(4);
		StockfishService service = new StockfishService(props);
		service.setEngineOption("Threads", Integer.toString(1));

		List<Future<EngineEvaluation>> done = new ArrayList<Future<EngineEvaluation>>();
		createTasks(service);
		
		while(!pending.isEmpty()) {

			for(Future<EngineEvaluation> result : pending) {
				if(result.isDone()) {
					done.add(result);
				}
			}
			
			for(Future<EngineEvaluation> complete : done) {
				pending.remove(complete);
			}
			
			done.clear();
			
			Thread.sleep(100);
			
		}
		
		service.destroy();
		
		assertTrue(pending.isEmpty());
	}

}
