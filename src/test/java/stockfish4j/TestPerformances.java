package stockfish4j;

import java.util.LinkedList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.junit.Ignore;
import org.junit.Test;

import stockfish4j.model.EngineEvaluation;
import stockfish4j.service.Stockfish4jProperties;
import stockfish4j.service.StockfishService;
import stockfish4j.utils.FenUtils;

@Ignore
public class TestPerformances {

	private LinkedList<String> performances = new LinkedList<String>();
	
	LinkedList<Future<EngineEvaluation>> results = new LinkedList<Future<EngineEvaluation>>();

	private void createTasks(StockfishService service) throws InterruptedException {
		service.cancelAll();
		for (String fen : FenUtils.getFens()) {
			results.add(service.submitDepthTask(fen, 20));
		}
	}

	@Test
	public void InstancesAndThreadsTest() throws InterruptedException {
		
		//the best option seems to be 4 instances and 1 thread
		
		int maxInstancies = 7;
		int maxThreads = 4;
		Stockfish4jProperties props = Stockfish4jProperties.getInstance();

		for (int i = 1; i <= maxInstancies; i++) {
			for (int j = 1; j <= maxThreads; j++) {

				props.setStockfishInstances(i);
				StockfishService service = new StockfishService(props);
				service.setEngineOption("Threads", Integer.toString(j));
				
				long startTime = System.currentTimeMillis();
				
				createTasks(service);

				for (Future<EngineEvaluation> result : results) {
					try {
						System.out.println(result.get().toString());
					} catch (InterruptedException e) {
						e.printStackTrace();
					} catch (ExecutionException e) {
						e.printStackTrace();
					}
				}

				long endTime = System.currentTimeMillis();

				System.out.println("All test fen tasks executed in " + (endTime - startTime)
						+ " millis on " + i + " instances and " + j + " threads");
				
				performances.add(i+"-"+j+": " + Long.toString(endTime - startTime));

				service.destroy();
			}
		}
		
		System.out.println(performances.toString());
	}

}
