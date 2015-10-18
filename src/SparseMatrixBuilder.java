import java.util.HashMap;

public class SparseMatrixBuilder {
    public HashMap<Indices, Integer> entries = new HashMap<>();
    private int n, m;
    SparseMatrixBuilder(int n, int m) {
        this.n = n;
        this.m = m;
    }
    public void put(int i, int j, int value) {
        entries.put(new Indices(i, j), value);
    }
    public int getHeight() {
        return n;
    }
    public int getWidth() {
        return m;
    }
}
