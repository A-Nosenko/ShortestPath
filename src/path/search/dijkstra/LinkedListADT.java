package path.search.dijkstra;

/**
 * Doubly linked list.
 *
 * @version 1.0 6/24/2019.
 */
public class LinkedListADT<T> {
    private NodeADT<T> head, tail;
    private int count;
    private NodeADT<T> nextNode;  // For operating as Iterator

    public LinkedListADT() {
        head = null;
        tail = null;
        count = 0;
        nextNode = null;
    }

    public void addToFirst(T element) {
        NodeADT<T> node = new NodeADT<T>(element);

        if (count == 0) {
            head = tail = node;
        } else {
            node.setNext(head);
            head.setPrev(node);
            head = node;
        }
        count++;
    }

    public void addToLast(T element) {
        NodeADT<T> node = new NodeADT<T>(element);

        if (count == 0) {
            head = tail = node;
        } else {
            node.setPrev(tail);
            tail.setNext(node);
            tail = node;
        }
        count++;
    }

    public T removeFirst() {
        if (count == 0)
            return null;
        else {
            T element = head.getElement();
            head = head.getNext();
            if (head == null)
                tail = null;
            else
                head.setPrev(null);
            count--;
            return element;
        }
    }

    public T removeLast() {
        if (count == 0)
            return null;
        else {
            T element = tail.getElement();
            tail = tail.getPrev();
            if (tail == null)
                head = null;
            else
                tail.setNext(null);
            count--;
            return element;
        }
    }

    public T first() {
        if (head == null)
            return null;
        else
            return head.getElement();
    }

    public T last() {
        if (tail == null)
            return null;
        else
            return tail.getElement();
    }

    public boolean isEmpty() {
        return count == 0;
    }

    public int size() {
        return count;
    }

    public NodeADT<T> head() {
        return head;
    }

    // Need to be careful when this method is used
    public void resize(int newSize) {
        count = newSize;
    }
  
  
  /*
   * The next three methods:
   *   For operating as Iterator
   */

    public void rewind() {
        nextNode = head;
    }

    public boolean hasNext() {
        return nextNode != null;
    }

    public T next() {
        if (nextNode == null)
            return null;

        T element = nextNode.getElement();
        nextNode = nextNode.getNext();

        return element;
    }

    public void remove() {
        NodeADT<T> prevNode;

        // get the previous node

        // if there is nothing
        if (nextNode == null) {
            if (tail == null)
                return;
                // next is after tail
            else
                prevNode = tail;
        }
        // next is in the linked list
        else
            prevNode = nextNode.getPrev();

        // remove prevNode.

        if (prevNode == null)
            ;
        else if (prevNode == head)
            removeFirst();
        else if (prevNode == tail)
            removeLast();
        else {
            prevNode.getPrev().setNext(prevNode.getNext());
            prevNode.getNext().setPrev(prevNode.getPrev());
            count--;
        }
    }
}
