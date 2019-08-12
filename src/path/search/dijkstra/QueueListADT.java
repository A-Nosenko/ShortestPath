package path.search.dijkstra;

/**
 * Queue using a doubly linked list.
 *
 * @version 1.0 6/24/2019.
 */
public class QueueListADT<T> {
    private LinkedListADT<T> linkedList;

    public QueueListADT() {
        this.linkedList = new LinkedListADT<T>();
    }

    public void enqueue(T element) {
        this.linkedList.addToLast(element);
    }

    public T dequeue() {
        return this.linkedList.removeFirst();
    }

    public T first() {
        return this.linkedList.first();
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
