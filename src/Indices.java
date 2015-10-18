public class Indices {
    public int i, j;

    public Indices(int i, int j) {
        this.i = i;
        this.j = j;
    }

    public int hashCode() {
        return (new Integer(i)).hashCode() * 127 + (new Integer(j)).hashCode();
    }

    public boolean equals(Indices b) {
        return (i == b.i) && (j == b.j);
    }

    public String toString() {
        return "(" + i + ", " + j + ")";
    }
}