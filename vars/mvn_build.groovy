import online.kairops.kna.skX.ioc.ContextRegistry
import online.kairops.kna.skX.pipelines.MvnPipeline

/**
 * Custom step for easy use of maven build inside Jenkinsfiles
 * @param settingsPath: Path to settings file
 * @param pomPath: Path to pom file
 * @return
 */
def call(HashMap config ) {
    ContextRegistry.registerMvnContext(this)

    // TODO - Check parameters

    withMaven( maven: 'Default') {

        def mvnPipeline = new MvnPipeline(config.get("settingsPath"),config.get("pomPath"), "ci")
        mvnPipeline.build()
    }
}