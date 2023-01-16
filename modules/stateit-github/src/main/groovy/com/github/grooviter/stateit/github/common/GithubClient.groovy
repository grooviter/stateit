package com.github.grooviter.stateit.github.common

import com.github.grooviter.stateit.core.Error
import com.github.grooviter.stateit.core.ErrorType
import com.github.grooviter.stateit.core.Resource
import com.github.grooviter.stateit.core.Result
import groovy.util.logging.Slf4j
import org.kohsuke.github.GitHub
import org.kohsuke.github.GitHubBuilder
import org.kohsuke.github.RateLimitChecker

@Slf4j
class GithubClient {
    static GitHub createGithubClient(Credentials credentials) {
        GitHubBuilder gitHubBuilder =  new GitHubBuilder().withOAuthToken(credentials.token, credentials.username)

        String rateLimit = System?.getenv("stateit.github.rate_limit")
        if (rateLimit?.isNumber()) {
            log.info("setting rate limit to $rateLimit")
            gitHubBuilder = gitHubBuilder.withRateLimitChecker(new RateLimitChecker.LiteralValue(rateLimit.toInteger()))
        }

        return gitHubBuilder.withEndpoint("http://localhost:8888").build()
    }

    static Result<Resource> handleException(Throwable th){
        return Result.error(new Error("stateit.github.client", th.message, ErrorType.EXECUTION.toString()))
    }
}
