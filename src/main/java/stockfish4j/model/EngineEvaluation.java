package stockfish4j.model;

import java.util.Scanner;

import stockfish4j.utils.FenUtils;

public class EngineEvaluation {

	private String fen;
	private String turn;
	private double evaluation;
	private String bestmove;
	private String pondermove;
	private boolean forcedMate;
	private boolean winningMate;
	private int mateLength;
	private int depth;
	private String line;
	private EvaluationType type;
	private long typeValue;
	private long executionTime;

	public EngineEvaluation(String fen) {
		this.fen = fen;
		this.turn = FenUtils.getTurn(fen);
		this.forcedMate = false;
		this.mateLength = 0;
	}

	public void setEngineResult(String engineResult) {
		Scanner sc = new Scanner(engineResult);
			
			sc.findInLine("bestmove");
			this.bestmove = sc.next();

			sc.findInLine("ponder");
			this.pondermove = sc.next();
			
			sc.findInLine("depth");
			String depthValue = sc.next();
			this.depth = Integer.parseInt(depthValue);

			sc.findInLine("score");
			String scoreType = sc.next();
			String value = sc.next();
			switch (scoreType) {
			case "cp":
				this.evaluation = Double.parseDouble(value) / 100;
				break;
			case "mate":
				this.mateLength = Integer.parseInt(value);
				this.forcedMate = true;
				this.winningMate = mateLength > 0 ? true : false;
				break;
			}

			sc.findInLine("pv");
			StringBuilder sb = new StringBuilder();
			while(sc.hasNext()) {
				sb.append(sc.next());
				sb.append(" ");
			}
			this.line = sb.toString();

		sc.close();
	}
	
	public String getEvaluationAsString() {
		
		if(isForcedMate()) {
			StringBuilder sb = new StringBuilder();
			String start = isWinningMate() ? "+" : "-";
			sb.append(start);
			sb.append("#");
			sb.append(this.mateLength);
			return sb.toString();
		}else {
			int factor = turn.equals("w") ? 1 : -1;
			return Double.toString(factor * this.evaluation);
		}
	}

	public String getFen() {
		return fen;
	}

	public double getEvaluation() {
		return evaluation;
	}

	public String getBestmove() {
		return bestmove;
	}

	public String getPondermove() {
		return pondermove;
	}

	public boolean isForcedMate() {
		return forcedMate;
	}

	public boolean isWinningMate() {
		return winningMate;
	}

	public int getMateLength() {
		return mateLength;
	}

	public String getLine() {
		return line;
	}

	public long getTypeValue() {
		return typeValue;
	}

	public void setType(EvaluationType type) {
		this.type = type;
	}

	public EvaluationType getType() {
		return type;
	}

	public void setTypeValue(long typeValue) {
		this.typeValue = typeValue;
	}

	public long getExecutionTime() {
		return executionTime;
	}

	public void setExecutionTime(long executionTime) {
		this.executionTime = executionTime;
	}

	public String getTurn() {
		return turn;
	}

	public void setTurn(String turn) {
		this.turn = turn;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public void setFen(String fen) {
		this.fen = fen;
	}

	public void setEvaluation(double evaluation) {
		this.evaluation = evaluation;
	}

	public void setBestmove(String bestmove) {
		this.bestmove = bestmove;
	}

	public void setPondermove(String pondermove) {
		this.pondermove = pondermove;
	}

	public void setForcedMate(boolean forcedMate) {
		this.forcedMate = forcedMate;
	}

	public void setWinningMate(boolean winningMate) {
		this.winningMate = winningMate;
	}

	public void setMateLength(int mateLength) {
		this.mateLength = mateLength;
	}

	public void setLine(String line) {
		this.line = line;
	}

	@Override
	public String toString() {
		return "EngineEvaluation [fen=" + fen + ", evaluation=" + evaluation + ", bestmove=" + bestmove
				+ ", pondermove=" + pondermove + ", forcedMate=" + forcedMate + ", winningMate=" + winningMate
				+ ", mateLength=" + mateLength + ", depth=" + depth + ", line=" + line + ", type=" + type
				+ ", typeValue=" + typeValue + ", executionTime=" + executionTime + "]";
	}

}
