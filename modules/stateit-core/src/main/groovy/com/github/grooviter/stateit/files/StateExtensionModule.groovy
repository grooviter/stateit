package com.github.grooviter.stateit.files

import com.github.grooviter.stateit.core.StateProps
import com.github.grooviter.stateit.core.StateProvider
import com.github.grooviter.stateit.files.state.FileStateProvider

/**
 * Provides a file state mechanism based on a local file
 *
 * @since 1.0.0
 */
class StateExtensionModule {
    /**
     * Creates an instance of {@link FileStateProvider}
     *
     * @param props part of the DSL where the method will be available
     * @param path string path where the state will be stored and retrieved
     * @return an instance of {@link StateProvider}
     * @since 1.0.0
     */
    static StateProvider fileState(StateProps props, String path) {
        return new FileStateProvider(path)
    }
}
