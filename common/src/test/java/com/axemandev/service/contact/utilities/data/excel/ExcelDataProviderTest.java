package com.axemandev.service.contact.utilities.data.excel;

import com.axemandev.service.contact.utilities.data.DataProvider;
import com.axemandev.service.contact.utilities.data.DataSource;
import com.axemandev.service.contact.utilities.data.excel.ExcelDataProvider;
import com.axemandev.service.contact.utilities.data.excel.ExcelDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ExcelDataProviderTest {

    DataSource dataSource;

    @BeforeEach
    void setUp() {
        dataSource = new ExcelDataSource("src/test/resources/file-source/ContactDetails.xlsx");
    }

    @Test
    void givenDataSource_whenFetchingData_thenReturnsDataAsListOfMap() {
        List<Map<Object, Object>> data = (new ExcelDataProvider()).getData(dataSource);
        assertEquals(5, data.size(), "incorrect records returned");
    }
}