public class SimpleVector implements Vector {
    private int[] elements;
    int length;
    SimpleVector() {
        this(2);
    }
    SimpleVector(int length) {
        this.length = length;
        elements = new int[length];
    }
    SimpleVector(int length, int[] elements) {
        this.length = length;
        this.elements = elements;
    }
    public int get(int i) {
        return elements[i];
    }
    public int getLength() {
        return length;
    }
}
