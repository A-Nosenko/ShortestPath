package path.search;

import java.text.DecimalFormat;

/**
 * Class to hold constants.
 *
 * @version 1.0 6/24/2019.
 */
public class Constants {
    public static final String TITLE = "PATH SEARCH";
    public static final int HIGHT = 400;
    public static final int WIDTH = 800;
    public static final int MIN_WIDTH_EXCEL_CELL = 2048;
    public static final String MAIN_FRAME = "/path/search/view/mainPane.fxml";
    public static final String CSS = "css/style.css";
    public static final String PROPERTIES = "last_destination.properties";
    public static final String LAST_FILE = "last_destination_file_path";
    public static final String FILE_NAME_BOUND = "\\.";
    public static final String TO = " -> ";
    public static final String EQUALS = " = ";
    public static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#0.#");
}
