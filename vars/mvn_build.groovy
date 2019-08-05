import online.kairops.kna.skX.ioc.ContextRegistry
import online.kairops.kna.skX.pipelines.MvnPipeline

/**
 * Custom step for easy use of maven build inside Jenkinsfiles
 * @param settingsPath: Path to settings file
 * @param pomPath: Path to pom file
 * @return
 */
def call(String settingsPath, String pomPath) {
    ContextRegistry.registerMvnContext(this)

    // TODO - Check parameters

    def mvnPipeline = new MvnPipeline(settingsPath, pomPath, "ci")
    mvnPipeline.build()
}