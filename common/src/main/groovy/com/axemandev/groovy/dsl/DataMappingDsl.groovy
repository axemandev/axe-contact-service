package com.axemandev.groovy.dsl

import com.axemandev.groovy.delegates.Config
import com.axemandev.groovy.delegates.Mapping

import static groovy.lang.Closure.DELEGATE_ONLY

class DataMappingDsl {
    private static Config config = new Config()
    private static Mapping mapping = new Mapping()

    static void mappings(Closure closure) {
        closure.delegate = { mapping(closure) }
        closure.call()
    }

    static void mapping(@DelegatesTo(value = Mapping, strategy = DELEGATE_ONLY) Closure closure) {
        closure.delegate = mapping
        closure.resolveStrategy = DELEGATE_ONLY
        closure.call()
    }

    static void config(@DelegatesTo(value = Config, strategy = DELEGATE_ONLY) Closure closure) {
        closure.delegate = config
        closure.resolveStrategy = DELEGATE_ONLY
        closure.call()
    }
}
