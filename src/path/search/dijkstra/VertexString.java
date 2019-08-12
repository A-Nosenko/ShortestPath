package path.search.dijkstra;

/**
 * Vertex of graph with String name.
 *
 * @version 1.0 6/24/2019.
 */
public class VertexString extends Vertex<String> {
    public VertexString(String content) {
        super(content);
    }

    public VertexString(int id, String content) {
        super(id, content);
    }

    public VertexString(int id, String content, VertexString parent) {
        super(id, content, parent);
    }
}