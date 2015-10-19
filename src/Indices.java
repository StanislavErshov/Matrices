public class Indices {
    public int i, j;

    public Indices(int i, int j) {
        this.i = i;
        this.j = j;
    }

    public int hashCode() {
        return (new Integer(i)).hashCode() + (new Integer(j)).hashCode();
    }

    public boolean equals(Object b) {
        if (!(b instanceof  Indices)) {
            return false;
        } else {
            Indices a = (Indices) b;
            return (i == a.i) && (j == a.j);
        }
    }

    public String toString() {
        return "(" + i + ", " + j + ")";
    }
}