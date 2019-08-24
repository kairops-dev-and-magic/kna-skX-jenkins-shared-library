
import online.kairops.kna.skX.ioc.ContextRegistry
import online.kairops.kna.skX.pipelines.AbstractCommonPipeline
import online.kairops.kna.skX.pipelines.CommonPipeline

import static online.kairops.kna.skX.pipelines.CommonPipeline.*

/**
 * Custom step for easy use of code analysis in Jenkinsfiles
 * @param
 * @return
 */
def call(HashMap config) {
    ContextRegistry.registerCommonContext(this)

    // TODO - Check parameters

    withSonarQubeEnv('Default') {
        String toolPathValue = ""
        String toolPathKey = ""
        switch (config.get(CODE_ANALYSIS_TYPE_KEY)){
            case "SONAR":
                toolPathKey = CODE_ANALYSIS_SONAR_PATH_KEY
                toolPathValue = tool 'SonarQubeScanner'
                break
            default:
                break
        }
        config.put(toolPathKey, toolPathValue)
        CommonPipeline commonPipeline = new CommonPipeline()
        commonPipeline.codeAnalysis config
    }
}
