package stockfish4j.service;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;

import stockfish4j.exception.StockfishException;
import stockfish4j.model.EngineEvaluation;
import stockfish4j.model.StockfishEngine;
import stockfish4j.model.EvaluationType;

public class EngineTask implements Callable<EngineEvaluation> {

	private ArrayBlockingQueue<StockfishEngine> engines;
	private String fen;
	private EvaluationType type;
	private long value;
	private Boolean cancel;
	private Boolean done;
	private StockfishEngine engine;

	public EngineTask(ArrayBlockingQueue<StockfishEngine> engines, String fen, EvaluationType type, long value) {

		this.engines = engines;
		this.fen = fen;
		this.type = type;
		this.value = value;
		this.cancel = false;
		this.done = false;
	}

	@Override
	public EngineEvaluation call() throws StockfishException {

		EngineEvaluation rval = createEngineEvaluation();

		if (!cancel) {
			try {
				engine = engines.remove();
				long startTime = System.currentTimeMillis();
				engine.setPosition(fen);
				engine.sendCommand(buildCommand());
				rval.setEngineResult(engine.readInput("bestmove"));
				long endTime = System.currentTimeMillis();
				rval.setExecutionTime(endTime - startTime);
				System.out.println(rval.toString());
				engines.add(engine);
			} catch (NoSuchElementException e) {
				e.printStackTrace();
				throw new StockfishException("Error while executing engine task");
			}

		}
		
		this.done = true;

		return rval;

	}

	public Boolean isDone() {
		return this.done;
	}

	public void stop() {

		if (!Objects.isNull(engine)) {
			engine.stop();
		} else {
			cancel = true;
		}

	}

	private String buildCommand() {
		StringBuilder sb = new StringBuilder("go ");
		switch (this.type) {
		case DEPTH:
			sb.append("depth ");
			sb.append(value);
			break;
		case MOVETIME:
			sb.append("movetime ");
			sb.append(value);
			break;
		case NODES:
			sb.append("nodes ");
			sb.append(value);
			break;
		case MATE:
			sb.append("mate ");
			sb.append(value);
			break;
		case INFINITE:
			sb.append("infinite");
			break;
		}

		return sb.toString();
	}

	private EngineEvaluation createEngineEvaluation() {

		EngineEvaluation rval = new EngineEvaluation(fen);
		rval.setType(type);
		rval.setTypeValue(value);

		return rval;
	}

	public ArrayBlockingQueue<StockfishEngine> getEngines() {
		return engines;
	}

	public String getFen() {
		return fen;
	}

	public EvaluationType getType() {
		return type;
	}

	public long getValue() {
		return value;
	}

}
