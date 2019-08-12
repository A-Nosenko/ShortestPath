package path.search.dijkstra;

/**
 * Stack using doubly linked list.
 *
 * @version 1.0 6/24/2019.
 */
public class StackListADT<T> {
    private LinkedListADT<T> linkedList;

    public StackListADT() {
        this.linkedList = new LinkedListADT<T>();
    }

    public void push(T element) {
        this.linkedList.addToLast(element);
    }

    public T pop() {
        T element = this.linkedList.removeLast();
        return element;
    }

    public T peek() {
        return this.linkedList.last();
    }

    public boolean isEmpty() {
        return this.linkedList.isEmpty();
    }

    public int size() {
        return this.linkedList.size();
    }

    public NodeADT<T> head() {
        return linkedList.head();
    }
}
