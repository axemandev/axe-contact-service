package com.axemandev.groovy.delegates

import com.axemandev.groovy.source.SourceType


class Config {
    private String absolutePath
    private SourceType sourceType

    void absolutePath(String absolutePathString) {
        absolutePath = absolutePathString
    }

    void sourceType(SourceType sourceTypeEnum) {
        sourceType = sourceTypeEnum
    }

    void showConfig() {
        println "Absolute Path: " + absolutePath + " | Source Type: " + sourceType
    }
}
