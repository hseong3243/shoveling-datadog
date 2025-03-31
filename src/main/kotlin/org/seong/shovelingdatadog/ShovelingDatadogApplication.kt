package org.seong.shovelingdatadog

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ShovelingDatadogApplication

fun main(args: Array<String>) {
    runApplication<ShovelingDatadogApplication>(*args)
}
