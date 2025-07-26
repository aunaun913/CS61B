import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {
    static class area{
        boolean opened;
        public area(){
            opened = false;
        }
    }
    area[][] map;
    int mapSize;
    int numOfOpenedArea = 0;
    WeightedQuickUnionUF uf;
    public int xyTo1D(int x, int y){
        return x * mapSize + y;
    }
    int virtualDown;
    int virtualUp;
    public Percolation(int N) {
        mapSize = N;
        map = new area[N][N];
        for(int i = 0; i < N; i++){
            for (int j = 0; j < N; j++){
                map[i][j] = new area();
            }
        }
        uf = new WeightedQuickUnionUF(N*N + 2);
        virtualDown = N*N;
        virtualUp = N*N + 1;
        for(int i = 0; i < N; i++){
            uf.union(xyTo1D(0, i), virtualUp);
            uf.union(xyTo1D(mapSize - 1, i), virtualDown);
        }
    }
    public void open(int row, int col) {
        if (map[row][col].opened) return;
        map[row][col].opened = true;
        if (row < mapSize-1 && map[row + 1][col].opened){
            uf.union(xyTo1D(row, col), xyTo1D(row + 1, col));
        }
        if (row != 0 && map[row - 1][col].opened) {
            uf.union(xyTo1D(row, col), xyTo1D(row - 1, col));
        }
        if (col < mapSize -1 && map[row][col + 1].opened) {
            uf.union(xyTo1D(row, col), xyTo1D(row, col + 1));
        }
        if (col != 0 && map[row][col - 1].opened) {
            uf.union(xyTo1D(row, col), xyTo1D(row, col - 1));
        }
        numOfOpenedArea++;
    }

    public boolean isOpen(int row, int col) {
        return map[row][col].opened;
    }

    public boolean isFull(int row, int col) {
        if (!map[row][col].opened) return false;
        return uf.connected(virtualUp, xyTo1D(row, col));
    }

    public int numberOfOpenSites() {
        return numOfOpenedArea;
    }

    public boolean percolates() {
        return uf.connected(virtualUp, virtualDown);
    }
}
