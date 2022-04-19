package com.axemandev.service.contact.utilities.data;

import java.util.List;
import java.util.Map;

public interface DataProvider {

    List<Map<Object, Object>> getData(DataSource dataSource);
}
