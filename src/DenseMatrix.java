public class DenseMatrix extends Matrix {
    int[][] entries;
    DenseMatrix() {
        this(1, 1);
    }
    DenseMatrix(int n, int m) {
        super(n, m);
        entries = new int[n][m];
    }
    DenseMatrix(int n, int m, int[][] entries) {
        super(n, m);
        this.entries = entries;
    }
    DenseMatrix(Matrix from) {
        this(from.getHeight(), from.getWidth());
        for (int i = 0; i < getHeight(); i++) {
            for (int j = 0; j < getWidth(); j++) {
                entries[i][j] = from.get(i, j);
            }
        }
    }

    public int get(int i, int j) {
        return entries[i][j];
    }

    //Slow multiplication
    public DenseMatrix multiply(DenseMatrix b) throws MatrixException {
        if (getWidth() != b.getHeight()) {
            throw new MatrixException("matrices couldn't be multiplied");
        }
        int resultN = getHeight();
        int resultM = b.getWidth();
        DenseMatrix result = new DenseMatrix(resultN, resultM);
        int k = getWidth();
        b = b.transpose();
        for (int i = 0; i < resultN; i++) {
            for (int j = 0; j < resultM; j++) {
                for (int r = 0; r < k; r++) {
                    result.entries[i][j] += entries[i][r] * b.entries[j][r];
                }
            }
        }
        return result;
    }

    //Very slow (but complicated) multiplication
    public DenseMatrix fastMultiply(DenseMatrix b) throws MatrixException {
        if (getWidth() != b.getHeight()) {
            throw new MatrixException("matrices couldn't be multiplied");
        }
        int n = 1;
        while (n < getWidth() || n < getHeight() || n < b.getWidth()) {
            n <<= 1;
        }
        int[][] A = new int[n][n];
        int[][] B = new int[n][n];
        for (int i = 0; i < getHeight(); i++) {
            for (int j = 0; j < getWidth(); j++) {
                A[i][j] = entries[i][j];
            }
        }
        for (int i = 0; i < b.getHeight(); i++) {
            for (int j = 0; j < b.getWidth(); j++) {
                B[i][j] = b.entries[i][j];
            }
        }
        FastMultiplier fm = new FastMultiplier(A, B, n);
        fm.multiply();
        int[][] result = new int[getHeight()][b.getWidth()];
        for (int i = 0; i < getHeight(); i++) {
            for (int j = 0; j < b.getWidth(); j++) {
                result[i][j] = fm.result[i][j];
            }
        }
        return new DenseMatrix(getHeight(), b.getWidth(), result);
    }

    public DenseMatrix transpose() {
        DenseMatrix result = new DenseMatrix(getWidth(), getHeight());
        for (int i = 0; i < getHeight(); i++) {
            for (int j = 0; j < getWidth(); j++) {
                result.entries[j][i] = entries[i][j];
            }
        }
        return result;
    }

    public Vector multiply(Vector b) throws MatrixException {
        if (b.getLength() != getWidth()) {
            throw new MatrixException("cannot multiply vector by matrix");
        }
        int[] result = new int[getHeight()];
        for (int i = 0; i < getHeight(); i++) {
            for (int j = 0; j < getWidth(); j++) {
                result[i] += entries[i][j] * b.get(j);
            }
        }
        return new SimpleVector(getHeight(), result);
    }

    public Vector multiplyLeft(Vector b) throws MatrixException {
        if (b.getLength() != getHeight()) {
            throw new MatrixException("cannot multiply vector by matrix");
        }
        int[] result = new int[getWidth()];
        for (int i = 0; i < getWidth(); i++) {
            for (int j = 0; j < getHeight(); j++) {
                result[i] += entries[j][i] * b.get(j);
            }
        }
        return new SimpleVector(getWidth(), result);
    }
}
