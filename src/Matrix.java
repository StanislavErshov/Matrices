public abstract class Matrix {
    private int n, m;

    Matrix(int n, int m) {
        this.n = n;
        this.m = m;
    }

    public abstract int get(int i, int j);

    public int getHeight() {
        return n;
    }

    public int getWidth() {
        return m;
    }

    public DenseMatrix multiply(Matrix b) throws MatrixException {
        DenseMatrix A = new DenseMatrix(this);
        DenseMatrix B = new DenseMatrix(b);
        return A.multiply(B);
    }

    public DenseMatrix add(Matrix b) throws MatrixException {
        if (n != b.getHeight() || m != b.getWidth()) {
            throw new MatrixException("matrices have different sizes");
        }
        int[][] result = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                result[i][j] = get(i, j) + b.get(i, j);
            }
        }
        return new DenseMatrix(n, m, result);
    }

    public DenseMatrix subtract(Matrix b) throws MatrixException {
        if (n != b.getHeight() || m != b.getWidth()) {
            throw new MatrixException("matrices couldn't be multiplied");
        }
        int[][] result = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                result[i][j] = get(i, j) - b.get(i, j);
            }
        }
        return new DenseMatrix(n, m, result);
    }

    public abstract Matrix transpose();

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("(\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                sb.append(get(i, j));
                if (j != m - 1) {
                    sb.append(" & ");
                }
            }
            if (i != n - 1) {
                sb.append("\\\\");
            }
            sb.append("\n");
        }
        sb.append(")\n");
        return sb.toString();
    }

    public Column getColumn(int col) {
        return new Column(this, col);
    }

    public Row getRow(int row) {
        return new Row(this, row);
    }

    public Vector multiply(Vector b) throws MatrixException {
        DenseMatrix a = new DenseMatrix(this);
        return a.multiply(b);
    }

    public Vector multiplyLeft(Vector b) throws MatrixException {
        DenseMatrix a = new DenseMatrix(this);
        return a.multiplyLeft(b);
    }

    public class Column implements Vector {
        private Matrix matrix;
        private int columnNumber;
        private int length;
        Column(Matrix matrix, int col) {
            length = matrix.getHeight();
            this.matrix = matrix;
            columnNumber = col;
        }
        public int get(int i) {
            return matrix.get(i, columnNumber);
        }
        public int getLength() {
            return length;
        }
    }

    public class Row implements Vector {
        private Matrix matrix;
        private int rowNumber;
        private int length;
        Row(Matrix matrix, int row) {
            length = matrix.getWidth();
            this.matrix = matrix;
            rowNumber = row;
        }
        public int get(int i) {
            return matrix.get(rowNumber, i);
        }
        public int getLength() {
            return length;
        }
    }

    public static DenseMatrix getZero(int size) {
        int[][] result = new int[size][size];
        return new DenseMatrix(size, size, result);
    }

    public static DenseMatrix getIdentity(int size) {
        int[][] result = new int[size][size];
        for (int i = 0; i < size; i++) {
            result[i][i] = 1;
        }
        return new DenseMatrix(size, size, result);
    }
}
