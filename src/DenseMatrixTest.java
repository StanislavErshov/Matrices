import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

public class DenseMatrixTest {
    public static final int SEED = 1;
    public static final int N = 1000;
    public static final int M = 1000;
    Random rnd = new Random(SEED);

    int[][] generateRandomIntArray(int n, int m) {
        int[][] array = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                array[i][j] = rnd.nextInt();
            }
        }
        return array;
    }
    @Test
    public void testMultiply() throws Exception {
        DenseMatrix a = new DenseMatrix(N, M, generateRandomIntArray(N, M));
        DenseMatrix b = new DenseMatrix(M, N, generateRandomIntArray(M, N));
        long startTime = System.nanoTime();
        DenseMatrix result = a.multiply(b);
        long estimatedTime = System.nanoTime() - startTime;
        System.out.println("Execution time(ms) slow multiply " + (estimatedTime/ 1000000));
        int[][] correct = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                for (int k = 0; k < M; k++) {
                    correct[i][j] += a.get(i, k) * b.get(k, j);
                }
            }
        }
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                assertTrue(result.get(i, j) == correct[i][j]);
            }
        }
    }
    @Test
    public void testFastMultiply() throws Exception {
        DenseMatrix a = new DenseMatrix(N, M, generateRandomIntArray(N, M));
        DenseMatrix b = new DenseMatrix(M, N, generateRandomIntArray(M, N));
        long startTime = System.nanoTime();
        DenseMatrix result = a.fastMultiply(b);
        long estimatedTime = System.nanoTime() - startTime;
        System.out.println("Execution time(ms) fast multiply " + (estimatedTime/ 1000000));
        int[][] correct = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                for (int k = 0; k < M; k++) {
                    correct[i][j] += a.get(i, k) * b.get(k, j);
                }
            }
        }
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                assertTrue(result.get(i, j) == correct[i][j]);
            }
        }
    }
}