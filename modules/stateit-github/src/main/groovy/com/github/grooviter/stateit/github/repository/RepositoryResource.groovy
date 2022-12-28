package com.github.grooviter.stateit.github.repository

import com.github.grooviter.stateit.core.Resource
import com.github.grooviter.stateit.core.Result
import com.github.grooviter.stateit.github.common.Credentials
import com.github.grooviter.stateit.github.common.CredentialsResolver
import com.github.grooviter.stateit.github.common.GithubClient
import groovy.transform.EqualsAndHashCode
import groovy.transform.TupleConstructor
import org.kohsuke.github.GHCreateRepositoryBuilder
import org.kohsuke.github.GHRepository
import org.kohsuke.github.GitHub

import static com.github.grooviter.stateit.github.repository.RepositoryErrors.ERROR_NAME_MISSING
import static com.github.grooviter.stateit.github.repository.RepositoryErrors.ERROR_OWNER_MISSING

@TupleConstructor
@EqualsAndHashCode(includes = ["id"])
class RepositoryResource extends Resource {
    String id
    RepositoryProps props

    @Override
    Result<Resource> applyWhenCreating() {
        return CredentialsResolver.resolveCredentials()
            .map(GithubClient::createGithubClient)
            .map { it.createRepository(props.name).owner(props.owner) }
            .mapTry({ it.create() }, GithubClient::handleException)
            .ifSuccessReturnThis(this) as Result<Resource>
    }

    @Override
    Result<Resource> applyWhenDestroying() {
        return CredentialsResolver.resolveCredentials()
            .map(GithubClient::createGithubClient)
            .map { it.createRepository(props.name).owner(props.owner) }
            .mapTry({ it.done().delete() }, GithubClient::handleException )
            .ifSuccessReturnThis(this) as Result<Resource>
    }

    @Override
    Result<Resource> validate() {
        if (!props.name) {
            return ERROR_NAME_MISSING.toResult(this) as Result<Resource>
        }

        if (!props.owner) {
            return ERROR_OWNER_MISSING.toResult(this) as Result<Resource>
        }

        Result<Credentials> credentialsResult = CredentialsResolver.resolveCredentials()

        if (credentialsResult.isFailure()) {
            return Result.error(this, credentialsResult.error) as Result<Resource>
        }

        return Result.of(this) as Result<Resource>
    }
}
