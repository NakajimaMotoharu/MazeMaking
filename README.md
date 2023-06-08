# MazeMaking
迷路作成・探索

このプログラムは、迷路の作成や自動探索するメソッドを持っています。またサンプルプログラムも付属しています。
int型二重配列を用いて、通路を0、壁を1、ルートを2とすることで迷路を表現しています。
メソッドは実行することでMaze型インスタンス内のデータを変更します。

以下メソッドの詳細

public Maze(int w, int h); // コンストラクタ(引数は両方帰趨を与えてください)

public void createMap(); // ランダムな迷路を作成します。

public void fillMap(int x, int y); // 指定座標から移動可能な位置をマークします。基本的にはデバッグ用です。

public int recursiveSearchMap(int x, int y, int a, int b, int come); // (x, y)から(a, b)までの経路探索(再起) 呼び出し時はcomeは-1を指定

public void randomSearchMap(int x1, int y1, int x2, int y2); // (x1, y1)から(x2, y2)までの経路探索(乱数)

public void printMap(); // マップを標準出力します。通路は' '、壁は'#'、ルートは'O'で表現します。

public void bufferSearch(int x1, int y1, int x2, int y2); // (x1, y1)から(x2, y2)までの経路探索(バッファー)

public void printBuffer(); // バッファー表示(標準出力)

public void resetRoot(); // マップから経路情報を削除

private boolean checkAllMine(); // すべての通路が開通しているかどうかの確認

private boolean checkAllDir(int x, int y); // 指定座標においてどこか一方向以上進行可能かどうかの確認

private boolean checkInMap(int x, int y); // 指定座標がマップ配列内にあるかどうかの確認

private int getMap(int x, int y); // 指定座標のマップ情報を取得

private void setMap(int x, int y, int p); // 指定座標のマップデータを置き換え

private int getBuffer(int x, int y); // 指定座標のバッファー情報を取得

private void setBuffer(int x, int y, int p); // 指定座標のバッファーデータを置き換え

private void makeBuffer(); // バッファーを作成

private int deleteBufferDeadEnd(int x1, int y1, int x2, int y2); // バッファーの行き止まりを削除

# UpdateLog
20230606 アップロード

20230608 探索アルゴリズムの追加・サンプルプログラムの修正
