package com.github.grooviter.stateit.cli

import com.github.grooviter.stateit.core.Result

trait ExitResultEvaluator {
    static Integer evalExitResult(Result result) {
        return result.success ? 0 : 1
    }
}