package path.search.dijkstra;

/**
 * The smaller priority, the higher.
 *
 * @version 1.0 6/24/2019.
 */
public class PriorityQueue<T> {
    private NodePriorityQueue<T> root;
    int numNodes;

    public PriorityQueue() {
        root = null;
        numNodes = 0;
    }

    public void addElement(T element) {
        addElement(0, element);
    }

    public void addElement(double priority, T element) {
        // When there is no node
        if (numNodes == 0) {
            root = new NodePriorityQueue<T>(element);
            root.setPriority(priority);
            numNodes++;
            return;
        }
        // Find the place where the new node goes to
        //   Actually the parent node
        NodePriorityQueue<T> parent;
        int position = numNodes;
        String s = position + "";

        while (position > 0) {
            if (position % 2 == 1)
                position = position / 2;
            else
                position = position / 2 - 1;
            s = position + "," + s;
        }
        String positions[] = s.split(",");

        parent = root;  // position == 0
        for (int i = 1; i < positions.length - 1; i++) {  // except the new position
            if (Integer.parseInt(positions[i]) % 2 == 1)
                parent = (NodePriorityQueue<T>) (parent.getLeft());
            else
                parent = (NodePriorityQueue<T>) (parent.getRight());
        }

        // Create the new node
        // Attach the new node to its parent

        NodePriorityQueue<T> nodeNew = new NodePriorityQueue<T>(element);
        nodeNew.setPriority(priority);
        if (numNodes % 2 == 1) {
            parent.setLeft(nodeNew);
            nodeNew.setParent(parent);
        } else {
            parent.setRight(nodeNew);
            nodeNew.setParent(parent);
        }
        numNodes++;

        // Adjust the heap

        NodePriorityQueue<T> node = nodeNew;
        while (node != root) {
            if (node.getPriority() < ((NodePriorityQueue<T>) (node.getParent())).getPriority())
                swapChildParent(node, (NodePriorityQueue<T>) (node.getParent()));
            else
                break;
        }
    }

    // New for Dijkstra
    public void addElement(NodePriorityQueue<T> nodeNew) {
        // When there is no node
        if (numNodes == 0) {
            root = nodeNew;
            numNodes++;
        } else {
            addElement(nodeNew.getPriority(), nodeNew.getContent());
        }
    }

    private void swapChildParent(NodePriorityQueue<T> node, NodePriorityQueue<T> parent) {
        NodePriorityQueue<T> grandParent, tmpGrandParentLeft;
        NodePriorityQueue<T> tmpParent, tmpParentLeft, tmpParentRight;
        NodePriorityQueue<T> tmpNodeLeft, tmpNodeRight;

        // Save the necessary pointers

        grandParent = (NodePriorityQueue<T>) (parent.getParent());
        if (grandParent != null) {
            tmpGrandParentLeft = (NodePriorityQueue<T>) (grandParent.getLeft());
        } else {
            tmpGrandParentLeft = null;
        }
        tmpParent = parent;
        tmpParentLeft = (NodePriorityQueue<T>) (parent.getLeft());
        tmpParentRight = (NodePriorityQueue<T>) (parent.getRight());
        tmpNodeLeft = (NodePriorityQueue<T>) (node.getLeft());
        tmpNodeRight = (NodePriorityQueue<T>) (node.getRight());

        // For node's child nodes

        if (tmpNodeLeft != null) tmpNodeLeft.setParent(parent);
        if (tmpNodeRight != null) tmpNodeRight.setParent(parent);

        // For parent that is swaped with node

        parent.setLeft(tmpNodeLeft);
        parent.setRight(tmpNodeRight);
        parent.setParent(node);

        // For node that becomes parent

        if (tmpParentLeft == node) {  // node is the left child
            node.setLeft(parent);
            node.setRight(tmpParentRight);
            node.setParent(null);
            if (tmpParentRight != null)
                tmpParentRight.setParent(node);
        } else {  // node is the right child
            node.setLeft(tmpParentLeft);
            node.setRight(parent);
            node.setParent(null);
            if (tmpParentLeft != null)
                tmpParentLeft.setParent(node);
        }

        // For grand parent

        if (grandParent == null) {  // then parent is the root.
            root = node;
        } else {
            node.setParent(grandParent);
            if (tmpGrandParentLeft == tmpParent) {  // parent is the left child
                grandParent.setLeft(node);
            } else {
                grandParent.setRight(node);
            }
        }
    }

    public T removeMin() {
        // If there is nothing, then return
        if (numNodes == 0)
            return null;

        // If there is only one, then remove the root and return its content
        if (numNodes == 1) {
            NodePriorityQueue<T> tmpRoot = root;
            root = null;
            numNodes = 0;
            return tmpRoot.getContent();
        }

        // Find the last node

        NodePriorityQueue<T> node;
        int position = numNodes - 1;
        String s = position + "";

        while (position > 0) {
            if (position % 2 == 1)
                position = position / 2;
            else
                position = position / 2 - 1;
            s = position + "," + s;
        }
        String positions[] = s.split(",");

        node = root;  // position == 0
        for (int i = 1; i < positions.length; i++) {
            if (Integer.parseInt(positions[i]) % 2 == 1)
                node = (NodePriorityQueue<T>) (node.getLeft());
            else
                node = (NodePriorityQueue<T>) (node.getRight());
        }

        // Cut off the last node

        if (node.getParent().getLeft() == node)
            node.getParent().setLeft(null);
        else
            node.getParent().setRight(null);

        numNodes--;

        // Keep the root node
        NodePriorityQueue<T> tmpRoot = root;

        // Make the last node as the root

        root = node;
        root.setParent(null);
        root.setLeft(tmpRoot.getLeft());
        root.setRight(tmpRoot.getRight());

        if (tmpRoot.getLeft() != null)
            tmpRoot.getLeft().setParent(root);
        if (tmpRoot.getRight() != null)
            tmpRoot.getRight().setParent(root);

        // Adjust the heap

        node = root;
        while (true) {
            if (node.getLeft() == null && node.getRight() == null)
                break;
            else if (node.getLeft() != null && node.getRight() == null) {
                if (node.getPriority() > ((NodePriorityQueue<T>) node.getLeft()).getPriority())
                    swapChildParent((NodePriorityQueue<T>) node.getLeft(), node);
                else
                    break;
            } else if (node.getLeft() == null && node.getRight() != null) {
                if (node.getPriority() > ((NodePriorityQueue<T>) node.getRight()).getPriority())
                    swapChildParent((NodePriorityQueue<T>) node.getRight(), node);
                else
                    break;
            } else {
                if (((NodePriorityQueue<T>) node.getLeft()).getPriority() <= ((NodePriorityQueue<T>) node.getRight()).getPriority()) {
                    if (node.getPriority() > ((NodePriorityQueue<T>) node.getLeft()).getPriority())
                        swapChildParent((NodePriorityQueue<T>) node.getLeft(), node);
                    else
                        break;
                } else if (((NodePriorityQueue<T>) node.getLeft()).getPriority() > ((NodePriorityQueue<T>) node.getRight()).getPriority()) {
                    if (node.getPriority() > ((NodePriorityQueue<T>) node.getRight()).getPriority())
                        swapChildParent((NodePriorityQueue<T>) node.getRight(), node);
                    else
                        break;
                } else
                    break;
            }
        }

        // Return the content of the root
        return tmpRoot.getContent();
    }

    public T findMin() {
        if (numNodes == 0)
            return null;
        else
            return getRoot().getContent();
    }

    public NodePriorityQueue<T> getRoot() {
        return root;
    }

    public boolean isEmpty() {
        return numNodes == 0;
    }

    public int size() {
        return numNodes;
    }
}