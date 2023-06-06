import java.util.Arrays;

public class Maze {
	private int[][] map;

	private int w, h;

	// コンストラクタ(引数は両方奇数を与えてください)
	public Maze(int w, int h){
		map = new int[h][w];
		for (int i = 0; i < h; i = i + 1){
			Arrays.fill(map[i], 1);
		}
		this.w = w;
		this.h = h;
	}

	// ランダムな迷路を作成します。
	public void createMap(){
		int x = (int)(Math.random() * (w / 2)) * 2 + 1;
		int y = (int)(Math.random() * (h / 2)) * 2 + 1;
		setMap(x, y, 0);
		while (checkAllMine()){
			if (checkAllDir(x, y)){
				while (true){
					x = (int)(Math.random() * (w / 2)) * 2 + 1;
					y = (int)(Math.random() * (h / 2)) * 2 + 1;
					if (!(checkAllDir(x, y)) && (getMap(x, y) == 0)){
						break;
					}
				}
			}
			while (true){
				int dir = (int)(Math.random() * 4);
				if (dir == 0){
					if (getMap(x + 2, y) == 1){
						setMap(x + 1, y, 0);
						setMap(x + 2, y, 0);
						x = x + 2;
						break;
					}
				} else if (dir == 1){
					if (getMap(x - 2, y) == 1){
						setMap(x - 1, y, 0);
						setMap(x - 2, y, 0);
						x = x - 2;
						break;
					}
				} else if (dir == 2){
					if (getMap(x, y + 2) == 1){
						setMap(x, y + 1, 0);
						setMap(x, y + 2, 0);
						y = y + 2;
						break;
					}
				} else if (dir == 3){
					if (getMap(x, y - 2) == 1){
						setMap(x, y - 1, 0);
						setMap(x, y - 2, 0);
						y = y - 2;
						break;
					}
				}
			}
		}
	}

	// すべての通路が開通しているかどうかの確認
	private boolean checkAllMine(){
		for (int i = 0; i < h / 2; i = i + 1){
			for (int j = 0; j < w / 2; j = j + 1){
				if (getMap(j * 2 + 1, i * 2 + 1) == 1){
					return true;
				}
			}
		}
		return false;
	}

	// 指定座標においてどこか一方向以上進行可能かどうかの確認
	private boolean checkAllDir(int x, int y){
		if (getMap(x + 2, y) == 1){
			return false;
		}
		if (getMap(x - 2, y) == 1){
			return false;
		}
		if (getMap(x, y + 2) == 1){
			return false;
		}
		if (getMap(x, y - 2) == 1){
			return false;
		}
		return true;
	}

	// 指定座標がマップ配列内にあるかどうかの確認
	private boolean checkInMap(int x, int y){
		return (((x >= 0) && (x < w)) && ((y >= 0) && (y < h)));
	}

	// 指定座標のマップ情報を取得
	private int getMap(int x, int y){
		if (checkInMap(x, y)){
			return map[y][x];
		}
		return -1;
	}

	// 指定座標のマップデータを置き換え
	private void setMap(int x, int y, int p){
		if (checkInMap(x, y)){
			map[y][x] = p;
		}
	}

	// 指定座標から移動可能な位置をマーク
	public void fillMap(int x, int y){
		setMap(x, y, 2);
		if (getMap(x + 1, y) == 0){
			fillMap(x + 1, y);
		}
		if (getMap(x - 1, y) == 0){
			fillMap(x - 1, y);
		}
		if (getMap(x, y + 1) == 0){
			fillMap(x, y + 1);
		}
		if (getMap(x, y - 1) == 0){
			fillMap(x, y - 1);
		}
	}

	// (x, y)から(a, b)までの経路探索
	public int searchMap(int x, int y, int a, int b){
		setMap(x, y, 2);
		if (x == a && y == b){
			return 1;
		}
		
		if (getMap(x + 1, y) == 0){
			if (searchMap(x + 1, y, a, b) == 0){
				setMap(x + 1, y, 0);
			} else {
				return 1;
			}
		}
		if (getMap(x - 1, y) == 0){
			if (searchMap(x - 1, y, a, b) == 0){
				setMap(x - 1, y, 0);
			} else {
				return 1;
			}
		}
		if (getMap(x, y + 1) == 0){
			if (searchMap(x, y + 1, a, b) == 0){
				setMap(x, y + 1, 0);
			} else {
				return 1;
			}
		}
		if (getMap(x, y - 1) == 0){
			if (searchMap(x, y - 1, a, b) == 0){
				setMap(x, y - 1, 0);
			} else {
				return 1;
			}
		}
		
		return 0;
	}

	// マップ表示(標準出力)
	public void printMap(){
		for (int i = 0; i < h; i = i + 1){
			for (int j = 0; j < w; j = j + 1){
				if (map[i][j] == 0){
					System.out.printf(" ");
				} else if (map[i][j] == 1){
					System.out.printf("#");
				} else if (map[i][j] == 2){
					System.out.printf("O");
				}
			}
			System.out.println();
		}
	}
}
