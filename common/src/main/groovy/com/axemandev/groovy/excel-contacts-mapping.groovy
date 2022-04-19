package com.axemandev.groovy

import com.axemandev.groovy.source.SourceType
import static com.axemandev.groovy.dsl.DataMappingDsl.*

config {
    absolutePath "C:\\Z\\Project\\support-files\\ContactDetails.xlsx"
    sourceType SourceType.EXCEL
}

mappings {
    mapping {
        setSource 'name'
        setTarget 'firstName'
        transform {
            splitOver ' '
            getTokenAt 1
        }

    }
}


