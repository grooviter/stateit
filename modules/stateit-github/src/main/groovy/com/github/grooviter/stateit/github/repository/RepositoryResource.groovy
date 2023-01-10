package com.github.grooviter.stateit.github.repository

import com.github.grooviter.stateit.core.Resource
import com.github.grooviter.stateit.core.ResourceConventions
import com.github.grooviter.stateit.core.Result
import com.github.grooviter.stateit.github.common.Credentials
import com.github.grooviter.stateit.github.common.CredentialsResolver
import com.github.grooviter.stateit.github.common.GithubClient
import groovy.transform.CompileDynamic
import org.kohsuke.github.GHCreateRepositoryBuilder
import org.kohsuke.github.GHRepository
import org.kohsuke.github.GitHub

import static com.github.grooviter.stateit.github.repository.RepositoryErrors.ERROR_NAME_MISSING
import static com.github.grooviter.stateit.github.repository.RepositoryErrors.ERROR_OWNER_MISSING

@ResourceConventions
class RepositoryResource extends Resource {
    String id
    RepositoryProps props

    @Override
    Result<Resource> applyWhenCreating() {
        return createGithubClient()
            .map(this::buildRepository)
            .mapTry(GHCreateRepositoryBuilder::create, GithubClient::handleException)
            .ifSuccessReturnThis(this) as Result<Resource>
    }

    @CompileDynamic
    private GHCreateRepositoryBuilder buildRepository(GitHub github) {
        GHCreateRepositoryBuilder builder = github
            .createRepository(props.name)
            .owner(props.owner)
            .private_(props.is_private)

        String[] ownerAndTemplate = props?.from_template?.split('/')
        if (ownerAndTemplate?.size() === 2) {
            return builder.fromTemplateRepository(ownerAndTemplate.first(), ownerAndTemplate.last())
        }

        if (props.default_branch) {
            return builder.defaultBranch(props.default_branch)
        }

        return builder
    }

    @Override
    Result<Resource> applyWhenDestroying() {
        return createGithubClient()
            .map { it.getRepository("${props.owner}/${props.name}") }
            .mapTry({ it.delete() }, GithubClient::handleException )
            .ifSuccessReturnThis(this) as Result<Resource>
    }

    private static Result<GitHub> createGithubClient() {
        return CredentialsResolver.resolveCredentials().map(GithubClient::createGithubClient)
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
