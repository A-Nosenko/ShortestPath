package path.search.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import path.search.Algorithm;
import path.search.file_support.FileWorkerXLSX;
import path.search.file_support.PropertyHandler;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.ResourceBundle;

import static path.search.Constants.*;

/**
 * Class to interact with user interface.
 *
 * @version 1.0 6/24/2019.
 */
public class MainController implements Initializable {

    private File sourceFile;
    private File resultFile;
    private String pathToSourceFile;
    private Algorithm algorithm = new Algorithm();
    FileWorkerXLSX fileWorkerXLSX = new FileWorkerXLSX();
    PropertyHandler propertyHandler = new PropertyHandler();
    Properties properties = propertyHandler.read(PROPERTIES);

    @FXML
    private TextField fileTextField;

    @FXML
    private Text actionTarget;

    @FXML
    private Button startButtonBellman;

    @FXML
    private Button startButtonDijkstra;

    @FXML
    private GridPane gridPane;

    @FXML
    public void open() {
        FileChooser fileChooser = new FileChooser();
        if (pathToSourceFile != null && !pathToSourceFile.isEmpty()) {
            try {
                fileChooser.setInitialDirectory(new File(pathToSourceFile).getParentFile());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("EXCEL Files", "*.xls", "*.xlsx"));
        Stage stage = (Stage) gridPane.getScene().getWindow();
        sourceFile = fileChooser.showOpenDialog(stage);
        if (sourceFile != null) {
            fileTextField.setText(sourceFile.getAbsolutePath());
        }
    }

    @FXML
    public void startTheAlgorithmBellman() {
        new Thread(() -> {
            long start = System.nanoTime();

            boolean faile = false;

            startButtonBellman.setDisable(true);
            startButtonDijkstra.setDisable(true);
            actionTarget.setText("Выполняется обработка данных...");

            try {
                pathToSourceFile = fileTextField.getText();
                if (pathToSourceFile == null || pathToSourceFile.isEmpty()) {
                    throw new Exception("Не удается распознать путь к файлу.");
                }
                File buffer = new File(pathToSourceFile);
                if (!buffer.exists()) {
                    throw new Exception("Файл не найден. \n" + pathToSourceFile);
                }

                sourceFile = buffer;

                resultFile = new File(createFileName(sourceFile.getAbsolutePath()));

                algorithm.calculateBellman(fileWorkerXLSX.read(sourceFile), fileWorkerXLSX, resultFile);

                saveLastSource();

            } catch (Exception e) {
                actionTarget.setText("Ошибка чтения файла \n" + pathToSourceFile + "\n" + e.getMessage());
                e.printStackTrace();
                return;
            } finally {
                startButtonBellman.setDisable(false);
                startButtonDijkstra.setDisable(false);
                init();
            }
            double finish = (System.nanoTime() - start) / 1000000000d;
            DecimalFormat decimalFormat = new DecimalFormat("#0.00");

            try {
                Desktop desktop = Desktop.getDesktop();
                if (!resultFile.exists()) {
                    throw new IOException();
                }
                desktop.open(resultFile);
            } catch (IOException e) {
                e.printStackTrace();
                faile = true;
            }

            if (!faile) {
                actionTarget.setText(
                        "Обработка графа завершена. Затрачено времени: " + decimalFormat.format(finish) + "c\n" +
                                "Результат сохранен в файле: \n" + resultFile.getAbsolutePath());
            } else {
                actionTarget.setText(
                        "Обработка графа завершена. Затрачено времени: " + decimalFormat.format(finish) + "c\n" +
                                "Ошибка чтения файла: \n" + resultFile.getAbsolutePath());
            }

        }).start();
    }

    @FXML
    public void startTheAlgorithmDijkstra() {
        new Thread(() -> {
            long start = System.nanoTime();

            boolean faile = false;

            startButtonBellman.setDisable(true);
            startButtonDijkstra.setDisable(true);
            actionTarget.setText("Выполняется обработка данных...");

            try {
                pathToSourceFile = fileTextField.getText();
                if (pathToSourceFile == null || pathToSourceFile.isEmpty()) {
                    throw new Exception("Не удается распознать путь к файлу.");
                }
                File buffer = new File(pathToSourceFile);
                if (!buffer.exists()) {
                    throw new Exception("Файл не найден. \n" + pathToSourceFile);
                }

                sourceFile = buffer;

                resultFile = new File(createFileName(sourceFile.getAbsolutePath()));

                algorithm.calculateDijkstra(fileWorkerXLSX.read(sourceFile), fileWorkerXLSX, resultFile);

                saveLastSource();

            } catch (Exception e) {
                actionTarget.setText("Ошибка чтения файла \n" + pathToSourceFile + "\n" + e.getMessage());
                e.printStackTrace();
                return;
            } finally {
                startButtonBellman.setDisable(false);
                startButtonDijkstra.setDisable(false);
                init();
            }
            double finish = (System.nanoTime() - start) / 1000000000d;
            DecimalFormat decimalFormat = new DecimalFormat("#0.00");

            try {
                Desktop desktop = Desktop.getDesktop();
                if (!resultFile.exists()) {
                    throw new IOException();
                }
                desktop.open(resultFile);
            } catch (IOException e) {
                e.printStackTrace();
                faile = true;
            }

            if (!faile) {
                actionTarget.setText(
                        "Обработка графа завершена. Затрачено времени: " + decimalFormat.format(finish) + "c\n" +
                                "Результат сохранен в файле: \n" + resultFile.getAbsolutePath());
            } else {
                actionTarget.setText(
                        "Обработка графа завершена. Затрачено времени: " + decimalFormat.format(finish) + "c\n" +
                                "Ошибка чтения файла: \n" + resultFile.getAbsolutePath());
            }

        }).start();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        init();
    }

    private void init() {
        String lastFile = properties.getProperty(LAST_FILE);
        if (lastFile != null && !lastFile.isEmpty()) {
            fileTextField.setText(lastFile);
            pathToSourceFile = lastFile;
        }
    }

    private String createFileName(String base) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd HH-mm-ss");
        return base.split(FILE_NAME_BOUND)[0] + " CALCULATION " + simpleDateFormat.format(new Date()) + ".xlsx";
    }

    private void saveLastSource() {
        properties.setProperty(LAST_FILE, pathToSourceFile);
        propertyHandler.write(properties, PROPERTIES);
    }
}
