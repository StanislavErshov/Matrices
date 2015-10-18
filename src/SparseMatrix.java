import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SparseMatrix extends Matrix {
    private HashMap<Indices, Integer> entries = new HashMap<>();

    SparseMatrix(int n, int m) {
        super(n, m);
    }

    SparseMatrix(SparseMatrixBuilder matrix) {
        super(matrix.getHeight(), matrix.getWidth());
        Iterator it = matrix.entries.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            entries.put((Indices) pair.getKey(), (Integer) pair.getValue());
        }
    }

    public int get(int i, int j) {
        Integer result = entries.get(new Indices(i, j));
        return result != null ? result : 0;
    }

    public SparseMatrix transpose() {
        SparseMatrixBuilder result = new SparseMatrixBuilder(getHeight(), getWidth());
        Iterator it = entries.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            result.put(((Indices) pair.getKey()).j, ((Indices) pair.getKey()).i,(int) pair.getValue());
        }
        return new SparseMatrix(result);
    }

    public LazyMultiplyMatrix lazyMultiply(SparseMatrix b) throws MatrixException {
        return new LazyMultiplyMatrix(this, b);
    }

    public class LazyMultiplyMatrix extends Matrix {
        private SparseMatrix a, b;
        HashMap<Indices, Integer> known = new HashMap<>();
        LazyMultiplyMatrix(SparseMatrix a, SparseMatrix b) throws MatrixException {
            super(a.getHeight(), b.getWidth());
            if (a.getWidth() != b.getHeight()) {
                throw new MatrixException("matrices couldn't be multiplied");
            }
            this.a = a;
            this.b = b;
        }

        public int get(int i, int j) {
            if (known.containsKey(new Indices(i, j))) {
                return known.get(new Indices(i, j));
            } else {
                int result = 0;
                Iterator it = a.entries.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry pair = (Map.Entry) it.next();
                    int ii = ((Indices) pair.getKey()).i;
                    int jj = ((Indices) pair.getKey()).j;
                    if (i == ii) {
                        result += (int) pair.getValue() * (b.entries.get(new Indices(jj, j)));
                    }
                }
                known.put(new Indices(i, j), result);
                return result;
            }
        }

        public LazyMultiplyMatrix transpose() {
            LazyMultiplyMatrix result = null;
            try {
                result = new LazyMultiplyMatrix(b.transpose(), a.transpose());
            } catch (MatrixException e) {
                //something impossible has happened :(
            }
            return result;
        }
    }
}
