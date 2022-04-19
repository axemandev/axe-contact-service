package com.axemandev.service.contact.utilities.data.excel;

import com.axemandev.service.contact.utilities.data.DataProvider;
import com.axemandev.service.contact.utilities.data.DataSource;
import com.axemandev.service.contact.utilities.exception.InvalidDataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

@Slf4j
public class ExcelDataProvider implements DataProvider {

    @Override
    public List<Map<Object, Object>> getData(DataSource dataSource) {
        validateDataSource(dataSource);
        return readDataFromSource(dataSource);
    }

    private void validateDataSource(DataSource dataSource) {
        assertIsExcelDataSource(dataSource);
    }

    private void assertIsExcelDataSource(DataSource dataSource) {
        ExcelDataSource expectedInstanceObject = new ExcelDataSource(null);
        if (!dataSource.getClass().isInstance(expectedInstanceObject))
            throw new InvalidDataSource("Invalid datasource, expected:ExcelDataSource got:" + dataSource.getClass().getName());
    }

    private List<Map<Object, Object>> readDataFromSource(DataSource dataSource) {
        try {
            return readDataFromFile((File) dataSource.getSource());
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return new ArrayList<>();
    }

    private List<Map<Object, Object>> readDataFromFile(File sourceFile) throws IOException {
        List<Map<Object, Object>> data = new ArrayList<>();
        Sheet sheet = getSheet(sourceFile, 0);
        List<String> header = getSheetHeader(sheet);

        for (int i = sheet.getFirstRowNum() + 1; i <= sheet.getLastRowNum(); i++) {
            data.add(getRowData(header, sheet.getRow(i)));
        }
        log.debug(convertToString(data));
        return data;
    }

    private List<String> getSheetHeader(Sheet sheet) {
        List<String> header = new LinkedList<>();
        sheet.getRow(sheet.getFirstRowNum()).forEach(cell -> {
            header.add(cell.getStringCellValue());
        });
        return header;
    }

    private Sheet getSheet(File sourceFile, Integer index) throws IOException {
        Workbook workbook = new XSSFWorkbook(new FileInputStream(sourceFile));
        Sheet sheet = workbook.getSheetAt(index);
        return sheet;
    }

    private Map<Object, Object> getRowData(List header, Row row) {
        Map<Object, Object> rowData = new HashMap<>();
        for (Cell cell : row) {
            String columnHeader = header.get(cell.getColumnIndex()).toString();
            log.info("ExcelDataProvider::getRowData - columnHeader = {}", columnHeader);
            switch (cell.getCellType().name()) {
                case "STRING":
                    rowData.put(columnHeader, cell.getStringCellValue());
                    break;
                case "NUMERIC":
                    rowData.put(columnHeader, cell.getNumericCellValue());
                    break;
                case "FORMULA":
                    rowData.put(columnHeader, cell.getCellFormula());
                    break;
                case "BOOLEAN":
                    rowData.put(columnHeader, cell.getBooleanCellValue());
                    break;
                case "BLANK":
                    rowData.put(columnHeader, null);
                    break;
            }
        }
        return rowData;
    }

    private String convertToString(List<Map<Object, Object>> data) {
        StringBuilder stringBuilder = new StringBuilder();
        int currentRowNum = 0;
        for(Map row: data) {
            for (Object key : row.keySet()) {
                stringBuilder.append("[" + currentRowNum + "][" + key + "]: " + row.get(key) + "\n");
            };
            currentRowNum++;
        };
        return stringBuilder.toString();
    }

}
