package stockfish4j.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

public class FenUtils {

	private static List<String> fens = new ArrayList<String>();

	public static String getRandomFen() {

		if (fens.isEmpty()) {
			loadFens();
		}

		int randomNum = ThreadLocalRandom.current().nextInt(0, fens.size());
		return fens.get(randomNum);

	}

	private static void loadFens() {
		String fileName = "src/test/resources/TestFens.txt";

		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {

			stream.forEach(l -> fens.add(l));

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public static List<String> getFens(){
		if (fens.isEmpty()) {
			loadFens();
		}
		return fens;
	}

	public static String getTurn(String fen) {
		Scanner sc = new Scanner(fen);
		sc.next();
		return sc.next();
	}

}
