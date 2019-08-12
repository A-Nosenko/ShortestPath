package path.search.dijkstra;

import java.util.ArrayList;
import java.util.List;

import static path.search.Constants.*;

/**
 * Dijkstra algorithm realization.
 *
 * @version 1.0 6/24/2019.
 */
public class DijkstraSearch {

    // Breadth-first traversal for a graph using a queue.
    public static String getShortestPath(int start, int finish, Graph graph) {
        if (graph == null) {
            return null;
        }
        graph.reset();
        NodePriorityQueueVertex[] nodes = new NodePriorityQueueVertex[graph.size()];
        StringBuilder result = new StringBuilder(); // Vertices have visited.

        PriorityQueue<VertexString> queue = new PriorityQueue<>();

        for (int i = 0; i < graph.size(); i++) {
            NodePriorityQueueVertex nodePriorityQueue = new NodePriorityQueueVertex(graph.find(i));
            if (i == start) {
                nodePriorityQueue.setPriority(0.0);
            } else {
                nodePriorityQueue.setPriority(Double.MAX_VALUE);
            }
            nodes[i] = nodePriorityQueue;
        }

        NodePriorityQueueVertex startNode = nodes[start];

        queue.addElement(startNode);

        while (!queue.isEmpty()) {

            VertexString currentVertex = queue.removeMin();

            if (currentVertex.isVisited()) {
                continue;
            }

            double currentCost = nodes[currentVertex.getId()].getPriority();
            for (int i = 0; i < graph.size(); i++) {
                if (i != currentVertex.getId()) {
                    double cost = graph.cost(currentVertex.getId(), i);
                    if (!graph.find(i).isVisited() && cost > 0 && cost < Double.MAX_VALUE) {
                        NodePriorityQueueVertex oldNode = nodes[i];
                        double bufferCost = cost + currentCost;
                        if (oldNode.getPriority() < bufferCost) {
                            continue;
                        }
                        graph.find(i).setParent(currentVertex);
                        NodePriorityQueueVertex nextVertex = nodes[i];
                        nextVertex.setPriority(bufferCost);
                        queue.addElement(nextVertex);
                    }
                }
            }
            currentVertex.visited(true);
        }

        VertexString finishVertex = graph.find(finish);
        double cost = nodes[finishVertex.getId()].getPriority();

        // Restoring path.
        StackListADT<VertexString> stack = new StackListADT<>();
        VertexString buffer = (VertexString) finishVertex.getParent();
        while (buffer != null) {
            stack.push(buffer);
            buffer = (VertexString)buffer.getParent();
        }

        List<VertexString> path = new ArrayList<>();

        int counter = 0;
        while (!stack.isEmpty()) {
            counter++;
            VertexString vertexString = stack.pop();
            path.add(vertexString);
            result.append(vertexString.getContent());
            if (counter > 4) {
                counter = 0;
                result.append("\n");
            }
            result.append(TO);
        }

        result.append(graph.find(finish).getContent());
        result.append(EQUALS);
        result.append(DECIMAL_FORMAT.format(cost));

        if (path.size() > 1) {
            path.add(finishVertex);
            for (int i = 1; i < path.size(); i++) {
                result.append("\n");
                result.append(path.get(i - 1).getContent());
                result.append(path.get(i).getContent());
                result.append(EQUALS);
                result.append(DECIMAL_FORMAT
                        .format(graph.cost(path.get(i - 1).getId(), path.get(i).getId())));
            }
        }

        return result.toString();
    }
}
