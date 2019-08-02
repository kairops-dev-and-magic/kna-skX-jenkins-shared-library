import online.kairops.kna.skX.ioc.ContextRegistry
import online.kairops.kna.skX.pipelines.MvnPipeline

/**
 * Example custom step for easy use of MsBuild inside Jenkinsfiles
 * @param solutionPath Path to .sln file
 * @return
 */
def call() {
    ContextRegistry.registerMvnContext(this)

    def mvnPipeline = new MvnPipeline()
    mvnPipeline.build()
}