package com.github.grooviter.stateit.github.common

import com.github.grooviter.stateit.core.Error
import com.github.grooviter.stateit.core.ErrorType
import com.github.grooviter.stateit.core.Resource
import com.github.grooviter.stateit.core.Result
import org.kohsuke.github.GitHub
import org.kohsuke.github.GitHubBuilder

class GithubClient {
    static GitHub createGithubClient(Credentials credentials) {
        return new GitHubBuilder().withOAuthToken(credentials.username, credentials.token).build()
    }

    static Result<Resource> handleException(Throwable th){
        return Result.error(new Error("stateit.github.client", th.message, ErrorType.EXECUTION.toString()))
    }
}
