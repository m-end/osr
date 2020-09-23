package reversi;

import java.util.ArrayList;
import java.util.List;

public class Board {

	private static String[][] field = new String[9][9];
	private static int[][] dir ={ {-1,-1},{0,-1},{1,-1},{1,0},{1,1},{0,1},{-1,1},{-1,0} };
	private static String black="●",white="○";
	private static String turn=black;
	private  List<Integer>stoneList = new ArrayList<>();
	private static List<Integer>gridList;

	public void init() {		//盤面の初期化
		for (int i = 1; i <= 8; i++) {
			for (int j = 1; j <= 8; j++) {
				field[i][j] = "・";
			}
		}
		for (int i = 1; i <= 8; i++) {
			field[0][i] = String.valueOf(i+" ");
			field[i][0] =String.valueOf(i+" ");
		}
		field[0][0] = "  ";
		field[4][4] = field[5][5] = black;
		field[4][5] = field[5][4] = white;
	}

	public void update() {		//盤面の更新
		for (String[] tmp : field) {
			for (String tmp_2 : tmp) {
				System.out.print(tmp_2);
			}
			System.out.println();
		}
	}

	public boolean check(int x,int y) {		//指定された座標に置けるか
		if(!(field[y][x].equals("・"))){return false;}
		boolean result=false;
			for(int i=0;i<dir.length;i++) {
				stoneList=new ArrayList<>();
				int dirX=x,dirY=y;
				dirX +=dir[i][0];
				dirY +=dir[i][1];
				if(dirX==9 ||dirX==-1 ||dirY==9 ||dirY==-1) { continue; }
				if(field[dirY][dirX].equals(white)) {
					while(true) {
						stoneList.add(dirY);
						stoneList.add(dirX);
						dirX +=dir[i][0];
						dirY +=dir[i][1];
						if(dirX==9 ||dirX==-1 ||dirY==9 ||dirY==-1) { break; }
						else if(field[dirY][dirX].equals(turn)) {				//置ける場合裏返すメソッドの呼び出し
							flip(stoneList);
							field[y][x]=turn;
							result=true;
							break;
						}
						else if(field[dirY][dirX].equals(white)) {
							continue;
							}
						else  break;
					}
				}
			}
			return result;
	}

	public void flip(List<Integer>stoneList) {			//石を裏返す
		for(int j=0;j<stoneList.size();j+=2) {
			if(turn.equals(black)) {
				field[stoneList.get(j)][stoneList.get(j+1)]=black;
			}
			else if(turn.equals(white)) {
				field[stoneList.get(j)][stoneList.get(j+1)]=white;
			}
		}
	}

	public void CheckTurn() {			//番手の判定
		if(turn.equals(black)) {
			System.out.println("黒(あなた)の番です");
		}
		else if(turn.equals(white)){
			System.out.println("白の番です");
			while(true) {
				if(cpu()){ break; };
			}
			shift();
			update();
			System.out.println("黒(あなた)の番です");
		}
	}

	public void scan() {				//置ける場所の探索
		gridList =new ArrayList<>();
		for(int i=0;i<field.length;i++) {
			for(int j=0;j<field.length;j++) {
				if(field[i][j].equals("・")) {
					gridList.add(j);
					gridList.add(i);
				}
			}
		}
	}

	public boolean cpu() {
		int x = 0, y = 0 ,count =0;
		boolean result = false;
		scan();
		out: for (int k = 0; k < gridList.size(); k += 2) {
			x = gridList.get(k);
			y = gridList.get(k + 1);
			for (int i = 0; i < dir.length; i++) {
				count++;
				stoneList = new ArrayList<>();
				int dirX = x, dirY = y;
				dirX += dir[i][0];
				dirY += dir[i][1];
				if (dirX == 9 || dirX == -1 || dirY == 9 || dirY == -1) {
					continue;
				}
				if (field[dirY][dirX].equals(black)) {
					while (true) {
						stoneList.add(dirY);
						stoneList.add(dirX);
						dirX += dir[i][0];
						dirY += dir[i][1];
						if (dirX == 9 || dirX == -1 || dirY == 9 || dirY == -1) {
							break;
						} else if (field[dirY][dirX].equals(turn)) {
							flip(stoneList);
							field[y][x] = turn;
							result = true;
							break out;
						} else if (field[dirY][dirX].equals(black)) {
							continue;
						}

						else
							break;
					}
				}
			}
			if(count==gridList.size()) {
			result = true;
			System.out.println("パスします");
			}
		}
		return result;
	}

	public void shift() {				//番手を入れ替える
		if(turn.equals(black)) {turn = white;}
		else if(turn.equals(white)) {turn = black;}
	}

	public boolean judge() {			//勝敗の判定
		int countBk = 0, countWt = 0;
		for (int i = 1; i <= 8; i++) {
			for (int j = 1; j <= 8; j++) {
				if (field[i][j].equals(black)) {
					countBk++;
				} else if (field[i][j].equals(white)) {
					countWt++;
				}
			}
		}
		if (countBk + countWt == 64) {
			if (countBk > countWt) {
				System.out.println("黒(あなた)の勝利");
			} else if (countBk == countWt) {
				System.out.println("引き分け");
			} else if (countWt > countBk) {
				System.out.println("白の勝利");
			}
			return true;
		}
		if (countWt == 0 && countBk > 4) {
			System.out.println("黒(あなた)の勝利");
			return true;
		}
		if (countBk == 0 && countWt > 4) {
			System.out.println("白の勝利");
			return true;
		}
		return false;
	}

}
