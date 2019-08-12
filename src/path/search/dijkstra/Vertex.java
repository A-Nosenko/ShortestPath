package path.search.dijkstra;

/**
 * Vertex of graph.
 *
 * @version 1.0 6/24/2019.
 */
public class Vertex<T> {
    private int id;
    private T content;
    private Vertex<T> parent;
    private boolean visited;
    private boolean expanded;

    public Vertex() {
        id = -1;
        content = null;
        parent = null;
        visited = false;
        expanded = false;
    }

    public Vertex(T content) {
        id = -1;
        this.content = content;
        parent = null;
        visited = false;
        expanded = false;
    }

    public Vertex(int id, T content) {
        this.id = id;
        this.content = content;
        parent = null;
        visited = false;
        expanded = false;
    }

    public Vertex(int id, T content, Vertex<T> parent) {
        this.id = id;
        this.content = content;
        this.parent = parent;
        visited = false;
        expanded = false;
    }

    public void reset(int id, T content) {
        this.id = id;
        this.content = content;
        parent = null;
        visited = false;
        expanded = false;
    }

    public void reset() {
        parent = null;
        visited = false;
        expanded = false;
    }

    public int getId() {
        return id;
    }

    public T getContent() {
        return content;
    }

    public Vertex<T> getParent() {
        return parent;
    }

    public void setParent(Vertex<T> parent) {
        this.parent = parent;
    }

    public boolean isVisited() {
        return visited;
    }

    public void visited() {
        visited = true;
    }

    public void visited(boolean b) {
        visited = b;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void expanded() {
        expanded = true;
    }

    public void expanded(boolean b) {
        expanded = b;
    }
}