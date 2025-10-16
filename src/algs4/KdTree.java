package algs4;

public class KdTree {

    private Node root;
    private int size;

    private static class Node {
        private final Point2D p;
        private Node lb;
        private Node rt;

        public Node(Point2D p) {
            this.p = p;
        }
    }

    public KdTree() {
        this.root = null;
        this.size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException("O ponto não pode ser nulo.");
        root = insert(root, p, true);
    }

    private Node insert(Node x, Point2D p, boolean vertical) {
        if (x == null) {
            size++;
            return new Node(p);
        }

        if (x.p.equals(p)) {
            return x;
        }

        if (vertical) {
            if (p.x() < x.p.x()) {
                x.lb = insert(x.lb, p, !vertical);
            } else {
                x.rt = insert(x.rt, p, !vertical);
            }
        } else {
            if (p.y() < x.p.y()) {
                x.lb = insert(x.lb, p, !vertical);
            } else {
                x.rt = insert(x.rt, p, !vertical);
            }
        }
        return x;
    }

    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException("O ponto não pode ser nulo.");
        return contains(root, p, true);
    }

    private boolean contains(Node x, Point2D p, boolean vertical) {
        if (x == null) return false;
        if (x.p.equals(p)) return true;

        if (vertical) {
            if (p.x() < x.p.x()) return contains(x.lb, p, !vertical);
            else return contains(x.rt, p, !vertical);
        } else {
            if (p.y() < x.p.y()) return contains(x.lb, p, !vertical);
            else return contains(x.rt, p, !vertical);
        }
    }

    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException("O ponto não pode ser nulo.");
        if (isEmpty()) return null;
        return nearest(root, p, root.p, true);
    }

    private Point2D nearest(Node x, Point2D p, Point2D champion, boolean vertical) {
        if (x == null) return champion;

        if (p.distanceSquaredTo(x.p) < p.distanceSquaredTo(champion)) {
            champion = x.p;
        }

        Node firstSubtree, secondSubtree;
        if (vertical) {
            if (p.x() < x.p.x()) {
                firstSubtree = x.lb;
                secondSubtree = x.rt;
            } else {
                firstSubtree = x.rt;
                secondSubtree = x.lb;
            }
        } else {
            if (p.y() < x.p.y()) {
                firstSubtree = x.lb;
                secondSubtree = x.rt;
            } else {
                firstSubtree = x.rt;
                secondSubtree = x.lb;
            }
        }

        champion = nearest(firstSubtree, p, champion, !vertical);

        double distToChampion = p.distanceSquaredTo(champion);
        double distToSplit;
        if (vertical) {
            distToSplit = (x.p.x() - p.x()) * (x.p.x() - p.x());
        } else {
            distToSplit = (x.p.y() - p.y()) * (x.p.y() - p.y());
        }

        if (distToSplit < distToChampion) {
            champion = nearest(secondSubtree, p, champion, !vertical);
        }

        return champion;
    }
}