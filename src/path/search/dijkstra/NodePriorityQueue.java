package path.search.dijkstra;

/**
 * Min priority queue node.
 *
 * @version 1.0 6/24/2019.
 */
public class NodePriorityQueue<T> extends NodeBinaryTree<T> {
    private double priority;  // The smaller, the higher

    public NodePriorityQueue(T content) {
        super(content);
    }

    public void setPriority(double p) {
        priority = p;
    }

    public double getPriority() {
        return priority;
    }
}