package stockfish4j;

import org.junit.Test;

import stockfish4j.service.Stockfish4jProperties;
import stockfish4j.service.StockfishService;
import stockfish4j.utils.FenUtils;

public class TestCancel {

	private void createTasks(StockfishService service) {

		for (String fen : FenUtils.getFens()) {
			service.submitDepthTask(fen, 24);
		}
		System.out.println("Tasks created and submitted");
	}

	@Test
	public void CancelTest() throws InterruptedException {

		Stockfish4jProperties props = Stockfish4jProperties.getInstance();

		props.setStockfishInstances(4);
		StockfishService service = new StockfishService(props);
		service.setEngineOption("Threads", Integer.toString(1));
		
		//first set of tasks

		createTasks(service);

		Thread.sleep(2000);	//computing for 2 seconds
		
		service.cancelAll();
		
		//second set of tasks
		
		createTasks(service);
		
		Thread.sleep(5000);	//computing for 5 seconds
		
		service.cancelAll();
		
		service.destroy();
	}

}
