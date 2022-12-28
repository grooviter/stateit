package com.github.grooviter.stateit.github.common

import com.github.grooviter.stateit.core.Error
import com.github.grooviter.stateit.core.ErrorType
import com.github.grooviter.stateit.core.Result

class CredentialsResolver {
    static Result<Credentials> resolveCredentials() {
        Map<String,String> env = System.getenv()
        String username = env.STATEIT_GITHUB_USERNAME
        String token = env.STATEIT_GITHUB_TOKEN

        if (!username || !token) {
            return Result.error(new Error("stateit.github.credentials.missing", "can't find credentials", ErrorType.VALIDATION.toString()))
        }

        return Result.of(new Credentials(username, token))
    }
}
