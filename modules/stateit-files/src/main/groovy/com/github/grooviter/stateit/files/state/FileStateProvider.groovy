package com.github.grooviter.stateit.files.state

import com.github.grooviter.stateit.core.Dependency
import com.github.grooviter.stateit.core.Error
import com.github.grooviter.stateit.core.Plan
import com.github.grooviter.stateit.core.Providers
import com.github.grooviter.stateit.core.Resource
import com.github.grooviter.stateit.core.Result
import com.github.grooviter.stateit.core.StateProvider
import groovy.json.JsonGenerator
import groovy.json.JsonSlurper
import groovy.transform.TupleConstructor
import groovy.util.logging.Slf4j

@Slf4j
@TupleConstructor
class FileStateProvider implements StateProvider {
    String path

    @Override
    Result<Plan> store(Plan planToStore) {
        JsonGenerator generator = new JsonGenerator.Options()
            .addConverter(Plan) { Plan plan, String key -> return plan.resourcesApplied }
            .addConverter(Resource) { Resource resource, String key ->  return [id: resource.id, props: resource.props, type: resource.getClass().name] }
            .addConverter(Dependency) { Dependency dependency, String key ->  return [parent: dependency.parent.id, target: dependency.targetField, source: dependency.sourceField] }
            .build()

        File stateFile = new File(this.path)
        stateFile.text = generator.toJson(planToStore)

        return Result.of(planToStore)
    }

    @Override
    Result<Plan> load() {
        File jsonFile = new File(path)

        if (!jsonFile.exists()) {
            log.info "state file not found"
            return Result.of(Plan.empty())
        }

        log.info "state file found... loading resources"
        List<Map<String, ?>> resourceList = new JsonSlurper().parse(jsonFile) as List<Map<String,?>>
        Providers providers = Providers.instance
        List<Resource> resources = resourceList
                .collect { Map<String, ?> resource ->
                    return providers.resolveSerdeByType(resource.type.toString())?.fromMap(resource)
                } as List<Resource>

        return Result.of(Plan.builder().resourcesInState(resources).build())
    }
}
