public class Sample {
	public static void main(String[] args) {
		// Maze型インスタンスを用意
		Maze maze = new Maze(31, 15);

		// 迷路作成
		maze.createMap();

		// 迷路探索
		maze.searchMap(1, 1, 29, 13);

		// 迷路表示(標準出力)
		maze.printMap();
	}
}
