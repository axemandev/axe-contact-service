package com.axemandev.service.contact.utilities.data.excel;

import com.axemandev.service.contact.utilities.data.DataSource;
import com.axemandev.service.contact.utilities.data.excel.ExcelDataSource;
import com.axemandev.service.contact.utilities.exception.InvalidFileException;
import com.axemandev.service.contact.utilities.exception.InvalidFileTypeException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.File;
import java.util.Properties;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class ExcelDataSourceTest {

    DataSource dataSource;
    static Properties testFiles;

    @BeforeAll
    static void setup() {
        testFiles = new Properties();
        testFiles.put("validExcelFile", "src/test/resources/file-source/ContactDetails.xlsx");
        testFiles.put("invalidExtensionFile", "src/test/resources/file-source/ContactDetails.txt");
        testFiles.put("noExtensionFile", "src/test/resources/file-source/support-files");
        testFiles.put("doesNotExistFile", "src/test/resources/file-source/FileDoesNotExist.xlsx");
    }

    private static Stream<Arguments> incorrectFileTypeTestArguments() {
        return Stream.of(
                Arguments.of(testFiles.get("invalidExtensionFile"),
                        ".txt is not a valid Excel file extension.",
                        "Exception message mismatch"),
                Arguments.of(testFiles.get("noExtensionFile"),
                        "<no-extension> is not a valid Excel file extension.",
                        "Exception message mismatch")
        );
    }

    @Test
    protected void givenValidExcelFile_whenCreatingExcelDataSource_thenReturnsValidFileHandle() {
        String fileUnderTest = (String)testFiles.get("validExcelFile");
        dataSource = ExcelDataSource.builder().absolutePath(fileUnderTest).build();
        assertNotNull(dataSource.getSource(), "Datasource should not be null");
        assertInstanceOf(File.class, dataSource.getSource(), "Excel datasource should return file handle");
    }

    @ParameterizedTest
    @MethodSource("incorrectFileTypeTestArguments")
    protected void givenIncorrectFileType_whenCreatingExcelDataSource_thenThrowsInvalidFileTypeException(
            String fileUnderTest, String expectedMessage, String message) {
        Exception exception = assertThrows(InvalidFileTypeException.class, () -> {
            ExcelDataSource.builder().absolutePath(fileUnderTest).build().getSource();
        });

        assertEquals(expectedMessage, exception.getMessage(), message);
    }

    @Test
    protected void givenInvalidFilePath_whenCreatingExcelDataSource_thenInvalidFileException() {
        String fileUnderTest = (String)testFiles.get("doesNotExistFile");
        String expectedMessage = "File " + fileUnderTest + " does not exist.";

        Exception exception = assertThrows(InvalidFileException.class, () -> {
            ExcelDataSource.builder().absolutePath(fileUnderTest).build().getSource();
        });
        assertEquals(expectedMessage, exception.getMessage(), "Exception message mismatch");
    }

}
