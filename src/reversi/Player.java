package reversi;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Player {



	public boolean match(String input) {		//数字か判定
		Pattern pattern = Pattern.compile("\\p{Digit}{2}");
		Matcher matcher =pattern.matcher(input);
		return matcher.matches();
	}

	public boolean select(String input,Board board,Player player) {			//プレイヤーの操作
		int x,y;

		if (input.equals("パス")) {
			 System.out.println("パスします");
			 return true;
		} else if (input.equals("リセット")) {
			 board.init();
			 return true;
		} else if (player.match(input)) {
			x = Integer.parseInt(input.substring(0, 1));
			y = Integer.parseInt(input.substring(1, 2));
			if(board.check(x, y)) {
				return true;
			}
			else {
				System.out.println("置けません");
				return false;
				}
		} else {
			 System.out.println("パス,座標,リセットのいずれかを入力");
			 return false;
		}
	}

}
