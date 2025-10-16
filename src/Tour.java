import algs4.StdDraw;
import algs4.StdOut;

public class Tour {
    private algs4.KdTree kdTree;

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
        if (useKdTree) {
            this.kdTree = new algs4.KdTree();
        }
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
        if (start == null) {
            start = new Node();
            start.point = p;
            start.next = start;
            count = 1;
            return;
        }

        Node bestPrevNode = null;
        double minDistance = Double.POSITIVE_INFINITY;

        Node currentNode = start;

        do {
            double currentDistance = currentNode.point.distanceTo(p);
            if (currentDistance < minDistance) {
                minDistance = currentDistance;
                bestPrevNode = currentNode;
            }
            currentNode = currentNode.next;
        } while (currentNode != start);

        Node newNode = new Node();
        newNode.point = p;
        assert bestPrevNode != null;
        newNode.next = bestPrevNode.next;
        bestPrevNode.next = newNode;
        count++;
    }

    public void insertNearestKd(Point p) {
        if (start == null) {
            start = new Node();
            start.point = p;
            start.next = start;
            count = 1;
            if (useKdTree) {
                String[] coords = p.toString().replace("(", "").replace(")", "").split(", ");
                double x = Double.parseDouble(coords[0]);
                double y = Double.parseDouble(coords[1]);
                kdTree.insert(new algs4.Point2D(x, y));
            }
            return;
        }

        String[] pCoords = p.toString().replace("(", "").replace(")", "").split(", ");
        double pX = Double.parseDouble(pCoords[0]);
        double pY = Double.parseDouble(pCoords[1]);
        algs4.Point2D queryPoint = new algs4.Point2D(pX, pY);

        algs4.Point2D nearestP2D = kdTree.nearest(queryPoint);

        Node currentNode = start;
        boolean found = false;
        do {
            String[] currentCoords = currentNode.point.toString().replace("(", "").replace(")", "").split(", ");
            double currentX = Double.parseDouble(currentCoords[0]);
            double currentY = Double.parseDouble(currentCoords[1]);

            if (currentX == nearestP2D.x() && currentY == nearestP2D.y()) {
                found = true;
                break;
            }
            currentNode = currentNode.next;
        } while (currentNode != start);


        Node newNode = new Node();
        newNode.point = p;
        newNode.next = currentNode.next;
        currentNode.next = newNode;
        count++;

        kdTree.insert(queryPoint);
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
