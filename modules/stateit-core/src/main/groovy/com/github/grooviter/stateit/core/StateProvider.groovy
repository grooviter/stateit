package com.github.grooviter.stateit.core

interface StateProvider {
    Result<Plan> store(Plan plan)
    Result<Plan> load()
}