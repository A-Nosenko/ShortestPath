package path.search.file_support;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import path.search.array_support.ArrayUtils;

import java.io.*;

import static path.search.Constants.*;

/**
 * Class to read and write EXCEL files.
 *
 * @version 1.0 6/24/2019.
 */
public class FileWorkerXLSX {

    private int rowCounter;
    private int cellCounter;
    private int[] cellMarker;
    private ArrayUtils arrayUtils = new ArrayUtils();

    public String[][] read(File file) {

        Workbook workbook = null;
        try (InputStream inputStream = new FileInputStream(file)) {

            // Ability to read new and old version EXCEL files.
            if (file.getName().endsWith("xlsx")) {
                workbook = new XSSFWorkbook(inputStream);
            } else {
                workbook = new HSSFWorkbook(inputStream);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        if (workbook == null) {
            System.err.println("WORKBOOK IS null");
            return null;
        }

        if (workbook.getNumberOfSheets() == 0) {
            System.err.println("EMPTY BOOK!");
            return null;
        }

        Sheet sheet = workbook.getSheetAt(0);
        if (sheet == null) {
            return null;
        }

        rowCounter = 0;
        String[][] result = new String[sheet.getPhysicalNumberOfRows()][];

        sheet.iterator().forEachRemaining((row) ->
        {
            String[] cells = new String[row.getPhysicalNumberOfCells()];
            cellCounter = 0;
            row.iterator().forEachRemaining(cell -> {
                int cellType = cell.getCellType();
                switch (cellType) {
                    case Cell.CELL_TYPE_STRING:
                        cells[cellCounter] = cell.getStringCellValue();
                        break;
                    case Cell.CELL_TYPE_NUMERIC:
                        cells[cellCounter] = String.valueOf(cell.getNumericCellValue());
                        break;
                    case Cell.CELL_TYPE_FORMULA:
                        cells[cellCounter] = String.valueOf(cell.getNumericCellValue());
                        break;
                    default:
                        cells[cellCounter] = null;
                        break;
                }
                cellCounter++;
            });
            result[rowCounter] = cells;
            rowCounter++;
        });

        int realCountWithoutNulls = 0;
        for (int i = 0; i < result[0].length; i++) {
            if (result[0][i] != null && !result[0][i].isEmpty()) {
                realCountWithoutNulls++;
            }
        }

        String[][] realResult = new String[realCountWithoutNulls][realCountWithoutNulls];
        for (int i = 0; i < realCountWithoutNulls; i++) {
            System.arraycopy(result[i], 0, realResult[i], 0, realResult.length);
        }

        return realResult;
    }

    public void write(String[][][] sourceFull, File destination) {
        Workbook workbook = new XSSFWorkbook();
        for (int s = 0; s < sourceFull.length; s++) {
            Sheet sheet;
            switch (s) {
                case 0:
                    sheet = workbook.createSheet("SHORTEST_PATHS");
                    break;
                case 1:
                    sheet = workbook.createSheet("INITIAL");
                    break;
                case 2:
                    sheet = workbook.createSheet("INTERMEDIATES");
                    break;
                default:
                    sheet = workbook.createSheet("sheet_" + s);
                    break;
            }
            String[][] source = sourceFull[s];
            Cell cell;
            CellStyle styleYELLOW = workbook.createCellStyle();
            CellStyle styleBASE = workbook.createCellStyle();
            styleBASE.setWrapText(true);
            styleBASE.setAlignment(CellStyle.ALIGN_CENTER);
            styleYELLOW.setAlignment(CellStyle.ALIGN_CENTER);
            styleYELLOW.setFillPattern(CellStyle.SOLID_FOREGROUND);
            styleYELLOW.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
            for (int i = 0; i < source.length; i++) {
                Row row = sheet.createRow(i);
                for (int j = 0; j < source[i].length; j++) {
                    cell = row.createCell(j);
                    if (cellMarker != null && s == 2 && arrayUtils.contains(cellMarker, j)) {
                        cell.setCellStyle(styleYELLOW);
                    } else {
                        cell.setCellStyle(styleBASE);
                    }
                    cell.setCellValue(source[i][j]);
                    sheet.autoSizeColumn(j);
                    if (sheet.getColumnWidth(j) < MIN_WIDTH_EXCEL_CELL) {
                        sheet.setColumnWidth(j, MIN_WIDTH_EXCEL_CELL);
                    }
                }
            }
        }

        try (FileOutputStream fileOut = new FileOutputStream(destination)) {
            workbook.write(fileOut);
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setCellMarker(int[] cellMarker) {
        this.cellMarker = cellMarker;
    }
}
