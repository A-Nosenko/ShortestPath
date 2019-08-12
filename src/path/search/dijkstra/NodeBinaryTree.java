package path.search.dijkstra;

/**
 * Binary tree element, contains links to parent, left and right elements.
 *
 * @version 1.0 6/24/2019.
 */
public class NodeBinaryTree<T> {
    private int id;
    private T content;
    private NodeBinaryTree<T> parent, left, right;

    public NodeBinaryTree() {
        this.id = -1;
        this.content = null;
        parent = null;
        left = null;
        right = null;
    }

    public NodeBinaryTree(T content) {
        this.id = -1;
        this.content = content;
        parent = null;
        left = null;
        right = null;
    }

    public NodeBinaryTree(int id, T content) {
        this.id = id;
        this.content = content;
        parent = null;
        left = null;
        right = null;
    }

    public int getId() {
        return id;
    }

    public T getContent() {
        return content;
    }

    public NodeBinaryTree<T> getParent() {
        return parent;
    }

    public void setParent(NodeBinaryTree<T> parent) {
        this.parent = parent;
    }

    public NodeBinaryTree<T> getLeft() {
        return left;
    }

    public void setLeft(NodeBinaryTree<T> left) {
        this.left = left;
    }

    public NodeBinaryTree<T> getRight() {
        return right;
    }

    public void setRight(NodeBinaryTree<T> right) {
        this.right = right;
    }
}