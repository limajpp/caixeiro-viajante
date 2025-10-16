import algs4.StdDraw;
import algs4.StdOut;

public class Tour {

    private static class Node {
        private Point point;
        private Node next;
    }

    private Node start;
    private int count;
    private final boolean useKdTree;

    public Tour() {
        this(false);
    }

    public Tour(boolean useKdTree) {
        this.useKdTree = useKdTree;
        this.start = null;
        this.count = 0;
    }

    public Tour(Point a, Point b, Point c, Point d) {
        this();
        insertNearestNaive(a);
        insertNearestNaive(b);
        insertNearestNaive(c);
        insertNearestNaive(d);
    }

    public int size() {
        return count;
    }

    public double length() {
        if (start == null || start.next == start)
            return 0.0;

        double total = 0.0;
        Node current = start;
        do {
            total += current.point.distanceTo(current.next.point);
            current = current.next;
        } while (current != start);
        return total;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (start == null)
            return "(Tour vazio)";

        Node current = start;
        do {
            sb.append(current.point.toString()).append("\n");
            current = current.next;
        } while (current != start);

        return sb.toString();
    }

    public void draw() {
        if (start == null || start.next == start)
            return;

        Node current = start;
        do {
            current.point.drawTo(current.next.point);
            current = current.next;
        } while (current != start);
    }

    public void insertNearest(Point p) {
        if (useKdTree) {
            insertNearestKd(p); // ainda não implementado
        } else {
            insertNearestNaive(p);
        }
    }

    public void insertNearestNaive(Point p) {
        throw new UnsupportedOperationException("Versão com KdTree não implementada.");
    }

    public void insertNearestKd(Point p) {
        throw new UnsupportedOperationException("Versão com lista não implementada.");
    }

    // Método de teste (opcional)
    public static void main(String[] args) {
        Tour tour = new Tour();
        tour.insertNearest(new Point(1.0, 1.0));
        tour.insertNearest(new Point(1.0, 4.0));
        tour.insertNearest(new Point(4.0, 4.0));
        tour.insertNearest(new Point(4.0, 1.0));

        StdOut.println("# de pontos = " + tour.size());
        StdOut.println("Comprimento = " + tour.length());
        StdOut.println(tour);

        StdDraw.setXscale(0, 6);
        StdDraw.setYscale(0, 6);
        tour.draw();
    }
}
