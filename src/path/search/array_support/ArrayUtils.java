package path.search.array_support;

import java.text.DecimalFormat;

/**
 * Class to hold methods those make easy work with arrays.
 *
 * @version 1.0 6/25/2019.
 */
public class ArrayUtils {

    /**
     * Deep equals realisation for arrays of double.
     *
     * @param array_1 First array.
     * @param array_2 Second array.
     * @return true if values in first arrays equals values in second array
     * with respective index.
     */
    public boolean deepEquals(double[] array_1, double[] array_2) {
        if (array_1.length != array_2.length) {
            return false;
        }

        for (int i = 0; i < array_1.length; i++) {
            if (array_1[i] != array_2[i]) {
                return false;
            }
        }
        return true;
    }

    /**
     * Conversion String values to numeric.
     *
     * @param source Array of String values.
     * @return Array of numeric values.
     */
    public double[][] convert(String[][] source) {
        double[][] result = new double[source.length - 1][];
        for (int i = 0; i < result.length; i++) {
            result[i] = new double[source[i].length - 1];
            for (int j = 0; j < result[i].length; j++) {
                try {
                    result[i][j] = Double.parseDouble(source[i + 1][j + 1].trim());
                } catch (Exception e) {
                    result[i][j] = Double.POSITIVE_INFINITY;
                }
                if (i == j && result[i][j] != 0) {
                    System.out.println(result[i][j]);
                    System.out.println(i + " " + j);
                    throw new RuntimeException("Incorrect source data. Must be 0 in matrix diagonal.");
                }
            }
        }
        return result;
    }

    /**
     * Conversion numeric values to String.
     *
     * @param source Array of String values that contains vertices names.
     * @param staff  Array of numeric values.
     * @return Array of String values.
     */
    public String[][] convert(String[][] source, double[][] staff) {
        String[][] result = new String[source.length][];
        DecimalFormat decimalFormat = new DecimalFormat("#0.#");
        for (int i = 0; i < result.length; i++) {
            result[i] = new String[source.length];
            for (int j = 0; j < result[i].length; j++) {
                if (i == 0 || j == 0) {
                    result[i][j] = source[i][j];
                } else {
                    result[i][j] = decimalFormat.format(staff[i - 1][j - 1]);
                }
            }
        }
        return result;
    }

    /**
     * Conversion numeric values to String.
     *
     * @param staff Array of numeric values.
     * @return Array of String values.
     */
    public String[][] convert(double[][] staff) {
        String[][] result = new String[staff.length][];
        DecimalFormat decimalFormat = new DecimalFormat("#0.#");
        for (int i = 0; i < result.length; i++) {
            result[i] = new String[staff[i].length];
            for (int j = 0; j < result[i].length; j++) {
                result[i][j] = decimalFormat.format(staff[i][j]);
            }
        }
        return result;
    }

    /**
     * Inverting array on 90°.
     *
     * @param staff initial array.
     * @return inverted on 90° initial array.
     */
    public String[][] invert(String[][] staff) {
        String[][] result = new String[staff[0].length][];
        for (int i = 0; i < result.length; i++) {
            result[i] = new String[staff.length];
            for (int j = 0; j < staff.length; j++) {
                result[i][j] = staff[j][i];
            }
        }
        return result;
    }

    /**
     * Finds min value in array.
     *
     * @param staff target array.
     * @return min value.
     */
    public double getMin(double[] staff) {
        if (staff == null || staff.length < 1) {
            throw new IllegalArgumentException("Incorrect array to get min value.");
        }

        double result = staff[0];
        for (double d : staff) {
            if (d < result) {
                result = d;
            }
        }
        return result;
    }

    /**
     * Method to check presence some number in the array.
     *
     * @param array target array.
     * @param value number to check.
     * @return true if array contains the number or false otherwise.
     */
    public boolean contains(int[] array, int value) {
        for (int i : array) {
            if (i == value) {
                return true;
            }
        }
        return false;
    }
}
