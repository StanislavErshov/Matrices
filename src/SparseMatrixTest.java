import org.junit.Test;

import java.util.HashMap;
import java.util.Random;

import static org.junit.Assert.*;

public class SparseMatrixTest {

    public final int N = 100;
    public final int M = 70;
    public final int COUNT = 1000;
    public final int SEED = 1;
    Random rnd = new Random(SEED);

    @Test
    public void testGet() throws Exception {
        long startTime = System.nanoTime();
        SparseMatrixBuilder mb = new SparseMatrixBuilder(N, M);
        int[][] dense = new int[N][M];
        for (int it = 0; it < COUNT; it++) {
            int i = rnd.nextInt(N);
            int j = rnd.nextInt(M);
            int value = rnd.nextInt() % 1000;
            mb.put(i, j, value);
            dense[i][j] = value;
        }
        SparseMatrix matrix = new SparseMatrix(mb);
        for (int it = 0; it < COUNT; it++) {
            int i = rnd.nextInt(N);
            int j = rnd.nextInt(M);
            assertTrue(dense[i][j] == matrix.get(i, j));
        }
        long estimatedTime = System.nanoTime() - startTime;
        System.out.println("Execution time(ms) get " + (estimatedTime/ 1000000));
    }

    @Test
    public void testLazyMultiply() throws Exception {
        long startTime = System.nanoTime();
        SparseMatrixBuilder mbA = new SparseMatrixBuilder(N, M);
        int[][] denseA = new int[N][M];
        for (int it = 0; it < COUNT; it++) {
            int i = rnd.nextInt(N);
            int j = rnd.nextInt(M);
            int value = rnd.nextInt() % 1000;
            mbA.put(i, j, value);
            denseA[i][j] = value;
        }

        SparseMatrixBuilder mbB = new SparseMatrixBuilder(M, N);
        int[][] denseB = new int[M][N];
        for (int it = 0; it < COUNT; it++) {
            int i = rnd.nextInt(M);
            int j = rnd.nextInt(N);
            int value = rnd.nextInt() % 1000;
            mbB.entries.put(new Indices(i, j), value);
            denseB[i][j] = value;
        }

        SparseMatrix A = new SparseMatrix(mbA);
        SparseMatrix B = new SparseMatrix(mbB);
        DenseMatrix denseResult = (new DenseMatrix(N, M, denseA)).multiply(
                        new DenseMatrix(M, N, denseB));
        Matrix result = A.lazyMultiply(B);

        for (int it = 0; it < COUNT; it++) {
            int i = rnd.nextInt(N);
            int j = rnd.nextInt(M);
            Integer a = result.get(i, j);
            assertTrue(denseResult.get(i, j) == (a == null ? 0 : a));
        }


        long estimatedTime = System.nanoTime() - startTime;
        System.out.println("Execution time(ms) multiply " + (estimatedTime/ 1000000));
    }
}