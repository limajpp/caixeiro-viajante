import algs4.Point2D;
import algs4.StdOut;
import java.util.HashMap;

public class Tour {

    private static class Node {
        private Point point;
        private Node next;
    }

    private Node start;
    private int count;
    private final boolean useKdTree;

    private algs4.KdTree kdTree;
    private HashMap<String, Node> pointStringToNodeMap;

    public Tour() {
        this(true);
    }

    public Tour(boolean useKdTree) {
        this.useKdTree = useKdTree;
        this.start = null;
        this.count = 0;
        if (useKdTree) {
            this.kdTree = new algs4.KdTree();
            this.pointStringToNodeMap = new HashMap<>();
        }
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
            insertNearestKd(p);
        } else {
            insertNearestNaive(p);
        }
    }

    public void insertNearestNaive(Point p) {
        Node newNode = new Node();
        newNode.point = p;

        if (start == null) {
            start = newNode;
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

        newNode.next = bestPrevNode.next;
        bestPrevNode.next = newNode;
        count++;
    }

    public void insertNearestKd(Point p) {
        Node newNode = new Node();
        newNode.point = p;
        String pString = p.toString();

        String[] coords = pString.replace("(", "").replace(")", "").split(", ");
        double x = Double.parseDouble(coords[0]);
        double y = Double.parseDouble(coords[1]);
        Point2D p2d = new Point2D(x, y);

        if (start == null) {
            start = newNode;
            start.next = start;
            count = 1;
            kdTree.insert(p2d);
            pointStringToNodeMap.put(pString, start);
            return;
        }

        Point2D nearestP2D = kdTree.nearest(p2d);

        Node bestPrevNode = pointStringToNodeMap.get(nearestP2D.toString());

        newNode.next = bestPrevNode.next;
        bestPrevNode.next = newNode;
        count++;

        kdTree.insert(p2d);
        pointStringToNodeMap.put(pString, newNode);
    }

    public static void main(String[] args) {
        Tour tourNaive = new Tour(false);
        runTest(tourNaive, "Teste Ingênuo");

        Tour tourKd = new Tour(true);
        runTest(tourKd, "Teste com KdTree");
    }

    private static void runTest(Tour tour, String name) {
        StdOut.println("--- " + name + " ---");
        tour.insertNearest(new Point(1.0, 1.0));
        tour.insertNearest(new Point(1.0, 4.0));
        tour.insertNearest(new Point(4.0, 4.0));
        tour.insertNearest(new Point(4.0, 1.0));

        StdOut.println("# de pontos = " + tour.size());
        StdOut.println("Comprimento = " + tour.length());
        StdOut.println(tour);
    }
}