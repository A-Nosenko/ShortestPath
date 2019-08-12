package path.search.dijkstra;

/**
 * Graph with vertices and costs between them.
 *
 * @version 1.0 6/24/2019.
 */
public class Graph {
    private VertexString vertices[];
    private double adjacencies[][];  // adjacent matrix
    private int numVertices;  // number of vertices

    public Graph(int numVertices) {
        vertices = new VertexString[numVertices];
        for (int i = 0; i < numVertices; i++)
            vertices[i] = null;

        adjacencies = new double[numVertices][];
        for (int i = 0; i < numVertices; i++) {
            adjacencies[i] = new double[numVertices];
            for (int j = 0; j < numVertices; j++)
                adjacencies[i][j] = -1;
        }

        this.numVertices = numVertices;
    }

    public void keepVertex(int id, VertexString vertex) {
        vertices[id] = vertex;
    }

    public void setNeighbors(int idFrom, int idTo, double cost) {
        adjacencies[idFrom][idTo] = cost;
    }

    public void reset() {
        for (int i = 0; i < numVertices; i++) {
            vertices[i].reset();
        }
    }

    public boolean[] getNeighbors(int id) {
        boolean neighbors[] = new boolean[numVertices];

        for (int i = 0; i < numVertices; i++)
            if (adjacencies[id][i] < 0)
                neighbors[i] = false;
            else
                neighbors[i] = true;

        return neighbors;
    }

    public double cost(int idFrom, int idTo) {
        return adjacencies[idFrom][idTo];
    }

    public boolean isEmpty() {
        return vertices[0] == null;
    }

    public int size() {
        return numVertices;
    }

    public VertexString find(int id) {
        if (id < 0)
            return null;
        else
            return vertices[id];
    }

    public VertexString find(String content) {
        for (int i = 0; i < numVertices; i++)
            if (vertices[i].getContent().equals(content))
                return vertices[i];

        return null;
    }
}