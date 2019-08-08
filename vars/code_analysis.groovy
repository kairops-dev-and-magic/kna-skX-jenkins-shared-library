
import online.kairops.kna.skX.ioc.ContextRegistry
import online.kairops.kna.skX.pipelines.CommonPipeline

/**
 * Custom step for easy use of code analysis inside Jenkinsfiles
 * @param
 * @return
 */
def call(HashMap config) {
    ContextRegistry.registerCommonContext(this)

    // TODO - Check parameters

    withSonarQubeEnv(sonarInstalation) {
        String toolPathValue = ""
        String toolPathKey = ""
        switch (config.get(CommonPipeline.CODE_ANALYSIS_TYPE_KEY)) {
            case CommonPipeline.CodeAnalysisTypes.SONAR:
                toolPathValue = tool 'SonarQubeScanner'
                toolPathKey = CommonPipeline.CODE_ANALYSIS_SONAR_PATH_KEY
                break
            default:
                break

        }
        config.put(toolPathKey, toolPathValue)
        CommonPipeline commonPipeline = new CommonPipeline()
        commonPipeline.codeAnalysis config
    }
}
