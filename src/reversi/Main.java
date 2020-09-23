package reversi;

import java.util.Scanner;

public class Main {
	public static void main(String[] args) {

		Board board = new Board();
		Player player = new Player();
		board.init();
		board.update();

		while (true) {
			if (board.judge()) {
				break;
			}
			board.CheckTurn();
			while (true) {
				Scanner sc = new Scanner(System.in);
				String input = sc.nextLine();
				if (player.select(input, board, player)) {
					break;
				}
			}
			board.shift();
			board.update();
		}

	}

}
