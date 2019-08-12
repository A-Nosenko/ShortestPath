package path.search.dijkstra;

/**
 * Node for double linked list.
 *
 * @version 1.0 6/24/2019.
 */
public class NodeADT<T> {
    private NodeADT<T> prev;
    private NodeADT<T> next;
    private T element;

    public NodeADT() {
        prev = null;
        next = null;
        element = null;
    }

    public NodeADT(T e) {
        prev = null;
        next = null;
        element = e;
    }

    public NodeADT<T> getNext() {
        return next;
    }

    public void setNext(NodeADT<T> n) {
        next = n;
    }

    public NodeADT<T> getPrev() {
        return prev;
    }

    public void setPrev(NodeADT<T> p) {
        prev = p;
    }

    public T getElement() {
        return element;
    }

    public void setElement(T e) {
        element = e;
    }
}
