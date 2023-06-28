package stockfish4j.service;

import java.util.TimerTask;

public class PruneTask extends TimerTask{
	
	private StockfishService service;
	
	public PruneTask(StockfishService service) {
		this.service = service;
	}

	@Override
	public void run() {
		service.pruneRunningTasks();
	}

}
