package path.search;

import path.search.array_support.ArrayUtils;
import path.search.dijkstra.DijkstraSearch;
import path.search.dijkstra.Graph;
import path.search.dijkstra.VertexString;
import path.search.file_support.FileWorkerXLSX;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import static path.search.Constants.*;

/**
 * Shortest path find algorithm realisation.
 *
 * @version 1.0 6/24/2019.
 */
public class Algorithm {
    private ArrayUtils arrayUtils = new ArrayUtils();

    /**
     * Method calculates graph via Dijkstra's algorithm and writes result to EXCEL file;
     *
     * @param source     Array of graph edges.
     * @param resultFile File to write results.
     */
    public void calculateDijkstra(String[][] source, FileWorkerXLSX fileWorkerXLSX, File resultFile) {

        String[][][] resultWithIntermediates = new String[2][][];

        double[][] initialMatrix = arrayUtils.convert(source);

        String[] vertices = new String[source.length - 1];
        System.arraycopy(source[0], 1, vertices, 0, vertices.length);

        Graph graph = new Graph(vertices.length);
        for (int i = 0; i < vertices.length; i++) {
            graph.keepVertex(i, new VertexString(i, vertices[i]));
        }

        for (int i = 0; i < initialMatrix.length; i++) {
            for (int j = 0; j < initialMatrix.length; j++) {
                if (initialMatrix[i][j] == Double.POSITIVE_INFINITY) {
                    graph.setNeighbors(i, j, -1);
                } else {
                    graph.setNeighbors(i, j, initialMatrix[i][j]);
                }
            }
        }

        String[][] shortestPaths = new String[source.length][source.length];
        StringBuilder[][] builders = new StringBuilder[source.length][source.length];

        for (int i = 0; i < source.length; i++) {
            for (int j = 0; j < source.length; j++) {
                builders[i][j] = new StringBuilder();
                if (i == 0) {
                    builders[i][j].append(source[0][j]);
                } else if (j == 0) {
                    builders[i][j].append(source[0][i]);
                } else {
                    builders[i][j].append(DijkstraSearch.getShortestPath(j - 1, i - 1, graph));
                }
            }
        }

        for (int i = 0; i < source.length; i++) {
            for (int j = 0; j < source.length; j++) {
                shortestPaths[i][j] = builders[i][j].toString();
            }
        }

        resultWithIntermediates[0] = shortestPaths;

        resultWithIntermediates[1] = arrayUtils.convert(source, initialMatrix);

        fileWorkerXLSX.write(resultWithIntermediates, resultFile);
    }

    /**
     * Method calculates graph via Bellman-Moore algorithm and writes result to EXCEL file;
     *
     * @param source     Array of graph edges.
     * @param resultFile File to write results.
     */
    public void calculateBellman(String[][] source, FileWorkerXLSX fileWorkerXLSX, File resultFile) {
        int[] markers = new int[source.length]; // Array to hold indexes to mark it yellow in the intermediate calculations.
        Arrays.fill(markers, -1);
        String[][][] resultWithIntermediates = new String[3][][];
        ArrayList<double[]> buffers = new ArrayList<>();

        double[][] initialMatrix = arrayUtils.convert(source);
        double[][] calculation = new double[initialMatrix.length][];
        double[] bufferMatrix;

        String[][] shortestPaths = new String[source.length][source.length];

        //////////////////////////////// ALGORITHM //////////////////////////////////////
        boolean nextRow;
        for (int i = 0; i < initialMatrix.length; i++) {
            bufferMatrix = new double[initialMatrix.length];
            nextRow = false;

            for (int j = 0; j < initialMatrix.length; j++) {
                if (j == i) {
                    bufferMatrix[j] = 0;
                } else {
                    bufferMatrix[j] = Double.POSITIVE_INFINITY;
                }
            }

            buffers.add(bufferMatrix);

            while (!nextRow) {
                bufferMatrix = arrayAddition(initialMatrix, bufferMatrix);
                buffers.add(bufferMatrix);
                if (buffers.size() > 1
                        && arrayUtils.deepEquals(buffers.get(buffers.size() - 1),
                        buffers.get(buffers.size() - 2))) {
                    markers[i] = buffers.size() - 1; // Remember the index to mark it yellow in the intermediate calculations.
                    nextRow = true;
                }
            }

            calculation[i] = bufferMatrix;
        }
        /////////////////////////////////////////////////////////////////////////////////

        for (int i = 0; i < source.length; i++) {
            for (int j = 0; j < source.length; j++) {
                if (j == 0) {
                    shortestPaths[i][j] = source[0][i];
                } else if (i == 0) {
                    shortestPaths[i][j] = source[0][j];
                }
            }
        }

        for (int i = 1; i < source.length; i++) {
            for (int j = 1; j < source.length; j++) {
                shortestPaths[i][j] = source[0][i]
                        + TO + source[0][j]
                        + EQUALS
                        + DECIMAL_FORMAT.format(calculation[i - 1][j - 1]);
            }
        }

        resultWithIntermediates[0] = shortestPaths;

        resultWithIntermediates[1] = arrayUtils.convert(source, initialMatrix);
        double[][] intermediates = new double[buffers.size()][];
        for (int i = 0; i < intermediates.length; i++) {
            intermediates[i] = buffers.get(i);
        }

        resultWithIntermediates[2] = arrayUtils.invert(arrayUtils.convert(intermediates));

        fileWorkerXLSX.setCellMarker(markers);
        fileWorkerXLSX.write(resultWithIntermediates, resultFile);
    }

    /**
     * Method to add transposed line to each column in the array.
     *
     * @param source     target array.
     * @param transposed transposed line.
     * @return array of minimum the sums.
     */
    private double[] arrayAddition(double[][] source, double[] transposed) {
        if (source.length != transposed.length) {
            throw new IllegalArgumentException("Incorrect arguments to addition the transposed line.");
        }
        double[] result = new double[transposed.length];
        double[][] sums = new double[result.length][];

        for (int i = 0; i < transposed.length; i++) {
            sums[i] = new double[transposed.length];
            for (int j = 0; j < transposed.length; j++) {
                sums[i][j] = source[j][i] + transposed[j];
            }
        }
        for (int i = 0; i < result.length; i++) {
            result[i] = arrayUtils.getMin(sums[i]);
        }
        return result;
    }

    private double getCost(String start, String finish, String[][] source) {
        int i = 0;
        int j = 0;
        for (String vertex : source[0]) {
            if (start.equals(vertex)) {
                break;
            }
            else i++;
        }
        for (String vertex : source[0]) {
            if (finish.equals(vertex)) {
                break;
            }
            else j++;
        }
        return Double.parseDouble(source[i][j]);
    }
}
