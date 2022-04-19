package com.axemandev.groovy.delegates

class Mapping {
    def source;
    def target;
    private def actions = new LinkedList<Closure>()

    def splitOver = { String delimiter -> "split ${source} over '${delimiter}'" }
    def getTokenAt = { Integer index -> println "return token at position ${index}" }

    def transform(Closure action) {
        action.call()
    }

}
