package stockfish4j.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StockfishEngine implements Runnable {

	private Process stockfish;
	private BufferedReader in;
	private BufferedWriter out;
	private String name;
	private String pathToEngine;
	private int verbosity;

	public StockfishEngine(String name, String pathToEngine, int verbosity) {

		this.name = name;
		this.pathToEngine = pathToEngine;
		this.verbosity = verbosity;
		run();


	}

	public void waitForReady() {
		sendCommand("isready");
		readInput("readyok");
	}

	public void sendCommand(String command) {

		try {
			out.write(command + "\n");
			out.flush();
			if (verbosity > 0) {
				System.out.println(">>> " + name + ": " + command);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String readInput(String expected) {
		String line;
		String lastInfo = "";
		String expectedLine = "";

		try {
			while ((line = in.readLine()) != null) {
				if (verbosity == 2) {
					System.out.println(line);
				}
				if (line.startsWith("info")) {
					lastInfo = line;
				}
				if (line.startsWith(expected)) {
					expectedLine = line;
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		StringBuilder sb = new StringBuilder();
		sb.append(expectedLine);
		sb.append(" ");
		sb.append(lastInfo);
		if (verbosity == 1) {
			System.out.println(" <  " + name + ": " + sb.toString());
		}
		return sb.toString();
	}
	
	public void setOption(String optionName, String value) {
		sendCommand("setoption name " + optionName + " value " + value);
	}
	
	public void newGame() {
		sendCommand("ucinewgame");
		setStartPosition();
		waitForReady();
	}
	
	public void setPosition(String fen) {
		sendCommand("position fen " + fen);
	}
	
	public void setStartPosition() {
		sendCommand("position startpos");
	}
	
	public void ponderHit() {
		sendCommand("ponderhit");
	}

	public void stop() {
		sendCommand("stop");
	}

	public void quit() {
		sendCommand("quit");
	}

	@Override
	public void run() {
		try {
			stockfish = Runtime.getRuntime().exec(pathToEngine);
			in = new BufferedReader(new InputStreamReader(stockfish.getInputStream()));
			out = new BufferedWriter(new OutputStreamWriter(stockfish.getOutputStream()));
			sendCommand("uci");
			readInput("uciok");
			waitForReady();
			setOptions();
			waitForReady();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void setOptions() {
		
		EngineOptions props = EngineOptions.getInstance();
		
		for(Object key : props.getPropertiesTable().keySet()) {
			
			setOption(((String) key).replaceAll("_", " "), (String) props.getPropertiesTable().getProperty((String)key));
			
		}
		
	}

}
