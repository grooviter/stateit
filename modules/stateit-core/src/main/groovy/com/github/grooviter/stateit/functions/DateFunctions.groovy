package com.github.grooviter.stateit.functions

import com.github.grooviter.stateit.DSL

/**
 * Functions available in DSL context regarding dates handling
 *
 * @since 1.0.0
 */
class DateFunctions {
    /**
     * Returns a string with the current date in the ISO-8601 format
     * using the pattern "yyyy-MM-dd'T'HH:mm:ssZ"
     *
     * @param dsl available in any DSL instance
     * @return a string representing the current moment in ISO8601 format
     * @since 1.0.0
     */
    static String nowISO8601(DSL dsl) {
        return new Date().format("yyyy-MM-dd'T'HH:mm:ssZ")
    }

    /**
     * Returns a string with the current date using the format pattern
     * passed as argument
     *
     * @param dsl available in any DSL instance
     * @param string with the pattern used to format current date
     * @return a string representing the current moment in the format passed as arg
     * @since 1.0.0
     */
    static String now(DSL dsl, String pattern) {
        return new Date().format(pattern)
    }
}
