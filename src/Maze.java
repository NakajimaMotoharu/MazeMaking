import java.util.Arrays;

public class Maze {
	private int[][] map;
	public int[][] buffer;

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

	// 指定座標のバッファー情報を取得
	private int getBuffer(int x, int y){
		if (checkInMap(x, y)){
			return buffer[y][x];
		}
		return -1;
	}

	// 指定座標のバッファーデータを置き換え
	private void setBuffer(int x, int y, int p){
		if (checkInMap(x, y)){
			buffer[y][x] = p;
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

	// (x, y)から(a, b)までの経路探索(再起) 呼び出し時はcomeは-1を指定
	public int recursiveSearchMap(int x, int y, int a, int b, int come){
		setMap(x, y, 2);
		if (x == a && y == b){
			return 1;
		}

		if (getMap(x + 1, y) == 0 && come != 0){
			if (recursiveSearchMap(x + 2, y, a, b, 1) == 0){
				setMap(x + 1, y, 0);
				setMap(x + 2, y, 0);
			} else {
				setMap(x + 1, y, 2);
				return 1;
			}
		}
		if (getMap(x - 1, y) == 0 && come != 1){
			if (recursiveSearchMap(x - 2, y, a, b, 0) == 0){
				setMap(x - 1, y, 0);
				setMap(x - 2, y, 0);
			} else {
				setMap(x - 1, y, 2);
				return 1;
			}
		}
		if (getMap(x, y + 1) == 0 && come != 2){
			if (recursiveSearchMap(x, y + 2, a, b, 3) == 0){
				setMap(x, y + 1, 0);
				setMap(x, y + 2, 0);
			} else {
				setMap(x, y + 1, 2);
				return 1;
			}
		}
		if (getMap(x, y - 1) == 0 && come != 3){
			if (recursiveSearchMap(x, y - 2, a, b, 2) == 0){
				setMap(x, y - 1, 0);
				setMap(x, y - 2, 0);
			} else {
				setMap(x, y - 1, 2);
				return 1;
			}
		}

		return 0;
	}

	// (x1, y1)から(x2, y2)までの経路探索(乱数)
	public void randomSearchMap(int x1, int y1, int x2, int y2){
		setMap(x1, y1, 2);
		while(true){
			if ((x1 == x2) && (y1 == y2)){
				break;
			}
			while(true){
				int dir = (int)(Math.random() * 4);
				if (dir == 0){
					int tmp = getMap(x1 + 1, y1);
					if (tmp == 0){
						setMap(x1 + 1, y1, 2);
						setMap(x1 + 2, y1, 2);
						x1 = x1 + 2;
						break;
					} else if (tmp == 2){
						setMap(x1, y1, 0);
						setMap(x1 + 1, y1, 0);
						x1 = x1 + 2;
						break;
					}
				} else if (dir == 1){
					int tmp = getMap(x1 - 1, y1);
					if (tmp == 0){
						setMap(x1 - 1, y1, 2);
						setMap(x1 - 2, y1, 2);
						x1 = x1 - 2;
						break;
					} else if (tmp == 2){
						setMap(x1, y1, 0);
						setMap(x1 - 1, y1, 0);
						x1 = x1 - 2;
						break;
					}
				} else if (dir == 2){
					int tmp = getMap(x1, y1 + 1);
					if (tmp == 0){
						setMap(x1, y1 + 1, 2);
						setMap(x1, y1 + 2, 2);
						y1 = y1 + 2;
						break;
					} else if (tmp == 2){
						setMap(x1, y1, 0);
						setMap(x1, y1 + 1, 0);
						y1 = y1 + 2;
						break;
					}
				} else if (dir == 3){
					int tmp = getMap(x1, y1 - 1);
					if (tmp == 0){
						setMap(x1, y1 - 1, 2);
						setMap(x1, y1 - 2, 2);
						y1 = y1 - 2;
						break;
					} else if (tmp == 2){
						setMap(x1, y1, 0);
						setMap(x1, y1 - 1, 0);
						y1 = y1 - 2;
						break;
					}
				}
			}
		}
	}

	// バッファーを作成
	private void makeBuffer(){
		buffer = new int[map.length][map[0].length];
		for (int i = 0; i < map.length; i = i + 1){
			for (int j = 0; j < map[i].length; j = j + 1){
				buffer[i][j] = map[i][j];
			}
		}
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

	// (x1, y1)から(x2, y2)までの経路探索(バッファー)
	public void bufferSearch(int x1, int y1, int x2, int y2){
		makeBuffer();
		while(deleteBufferDeadEnd(x1, y1, x2, y2) != 0){}

		for (int x = 0; x < w; x = x + 1){
			for (int y = 0; y < h; y = y + 1){
				if (getBuffer(x, y) == 0){
					setMap(x, y, 2);
				}
			}
		}
	}

	// バッファーの行き止まりを削除
	private int deleteBufferDeadEnd(int x1, int y1, int x2, int y2){
		int hide = 0;

		for (int x = 1; x < w; x = x + 2){
			for (int y = 1; y < h; y = y + 2){
				if (getBuffer(x, y) == 0){
					if ((x1 == x) && (y1 == y)){

					} else if ((x2 == x) && (y2 == y)){

					} else {
						int dead = 0;
						if (getBuffer(x + 1, y) == 1){
							dead = dead + 1;
						}
						if (getBuffer(x - 1, y) == 1){
							dead = dead + 1;
						}
						if (getBuffer(x, y + 1) == 1){
							dead = dead + 1;
						}
						if (getBuffer(x, y - 1) == 1){
							dead = dead + 1;
						}
						if (dead >= 3){
							setBuffer(x, y, 1);
							setBuffer(x + 1, y, 1);
							setBuffer(x - 1, y, 1);
							setBuffer(x, y + 1, 1);
							setBuffer(x, y - 1, 1);
							hide = hide + 1;
						}
					}
				}
			}
		}

		return hide;
	}

	// バッファー表示(標準出力)
	public void printBuffer(){
		for (int i = 0; i < h; i = i + 1){
			for (int j = 0; j < w; j = j + 1){
				if (buffer[i][j] == 0){
					System.out.printf(" ");
				} else if (buffer[i][j] == 1){
					System.out.printf("#");
				} else if (buffer[i][j] == 2){
					System.out.printf("O");
				}
			}
			System.out.println();
		}
	}

	// マップから経路情報を削除
	public void resetRoot(){
		for (int x = 0; x < w; x = x + 1){
			for (int y = 0; y < h; y = y + 1){
				if (getMap(x, y) == 2){
					setMap(x, y, 0);
				}
			}
		}
	}
}
