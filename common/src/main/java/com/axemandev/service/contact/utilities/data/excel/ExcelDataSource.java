package com.axemandev.service.contact.utilities.data.excel;

import com.axemandev.service.contact.utilities.data.DataSource;
import com.axemandev.service.contact.utilities.exception.InvalidFileException;
import com.axemandev.service.contact.utilities.exception.InvalidFileTypeException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.File;

@Slf4j
@Data
@Builder
@AllArgsConstructor
public class ExcelDataSource implements DataSource {

    private String absolutePath;

    @Override
    public File getSource() {
        log.debug("ExcelDataSource::getDataSource - Source file: {}", absolutePath);
        File file = new File(absolutePath);
        validate(file);
        return file;
    }

    private void validate(File file) {
        assertHasValidExtension(file.getName());
        assertIsAValidFile(file);
    }

    private void assertHasValidExtension(String fileName) {
        String fileExtension = getFileExtension(fileName);

        log.debug("Validating file extension: {}", fileExtension);
        if (!fileExtension.equals(".xsl") && !fileExtension.equals(".xlsx"))
            throw new InvalidFileTypeException(fileExtension + " is not a valid Excel file extension.");
    }

    private String getFileExtension(String fileName) {
        Integer extensionSeparatorIndex = fileName.lastIndexOf('.');
        return extensionSeparatorIndex == -1 ? "<no-extension>" : fileName.substring(fileName.lastIndexOf('.'));
    }

    private void assertIsAValidFile(File file) {
        if (!file.exists())
            throw new InvalidFileException("File " + absolutePath + " does not exist.");

        if (!file.isFile())
            throw new InvalidFileException("Object " + absolutePath + " is not a file.");
    }
}
