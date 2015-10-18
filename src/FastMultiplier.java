public class FastMultiplier { //Very slow multiplication
    public int[][] a;
    public int[][] b;
    public int[][] result;
    public int size;
    public int aPosI, aPosJ, bPosI, bPosJ, resPosI, resPosJ;
    public final int minSize = 8;

    FastMultiplier(int[][] a, int[][] b, int size) {
        this.a = a;
        this.b = b;
        this.size = size;
        this.result = new int[size][size];
        aPosI = aPosJ = bPosI = bPosJ = resPosI = resPosJ = 0;
    }
    FastMultiplier(FastMultiplier fm, int aPosI, int aPosJ,
                   int bPosI, int bPosJ, int resPosI, int resPosJ, int size) {
        this.a = fm.a;
        this.b = fm.b;
        this.result = fm.result;
        this.size= size;
        this.aPosI = aPosI;
        this.aPosJ = aPosJ;
        this.bPosI = bPosI;
        this.bPosJ = bPosJ;
        this.resPosI = resPosI;
        this.resPosJ = resPosJ;
    }

    void multiply() {
        if (size <= minSize) {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    for (int k = 0; k < size; k++) {
                        result[resPosI + i][resPosJ + j] += a[aPosI + i][aPosJ + k] * b[bPosI + k][bPosJ + j];
                    }
                }
            }
        } else {
            (new FastMultiplier(this, aPosI, aPosJ,
                    bPosI, bPosJ,
                    resPosI, resPosJ, size / 2)).multiply();
            (new FastMultiplier(this, aPosI, aPosJ + size / 2,
                    bPosI + size / 2, bPosJ,
                    resPosI, resPosJ, size / 2)).multiply();

            (new FastMultiplier(this, aPosI, aPosJ,
                    bPosI, bPosJ + size / 2,
                    resPosI, resPosJ + size / 2, size / 2)).multiply();
            (new FastMultiplier(this, aPosI, aPosJ + size / 2,
                    bPosI + size / 2, bPosJ + size / 2,
                    resPosI, resPosJ + size / 2, size / 2)).multiply();


            (new FastMultiplier(this, aPosI + size / 2, aPosJ,
                    bPosI, bPosJ,
                    resPosI + size / 2, resPosJ, size / 2)).multiply();
            (new FastMultiplier(this, aPosI + size / 2, aPosJ + size / 2,
                    bPosI + size / 2, bPosJ,
                    resPosI + size / 2, resPosJ, size / 2)).multiply();

            (new FastMultiplier(this, aPosI + size / 2, aPosJ,
                    bPosI, bPosJ + size / 2,
                    resPosI + size / 2, resPosJ + size / 2, size / 2)).multiply();
            (new FastMultiplier(this, aPosI + size / 2, aPosJ + size / 2,
                    bPosI + size / 2, bPosJ + size / 2,
                    resPosI + size / 2, resPosJ + size / 2, size / 2)).multiply();
        }
    }
}
