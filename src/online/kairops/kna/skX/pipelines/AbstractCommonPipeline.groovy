package online.kairops.kna.skX.pipelines

import online.kairops.kna.skX.IStepExecutor
import online.kairops.kna.skX.ioc.ContextRegistry

abstract class AbstractCommonPipeline implements IPipeline, Serializable {

    enum CodeAnalysisTypes {
        SONAR ("SONAR"),

        private String _type

        CodeAnalysisTypes(String type){
            this._type = type
        }

        String getType() {
            _type
        }

        String toString() {
            return name() + " = " + _type
        }

    }

    public final static String CODE_ANALYSIS_TYPE_KEY = "type"
    public final static String CODE_ANALYSIS_SONAR_PATH_KEY = "SONAR_SCANNER_PATH"
    protected final static String CODE_ANALYSIS_SONAR_COMMAND = "/bin/sonar-scanner"

    @Override
    int codeAnalysis(Map<String,String> analysis){

        IStepExecutor steps = ContextRegistry.getContext().getStepExecutor()

        CodeAnalysisTypes analysisType = analysis.get(CODE_ANALYSIS_TYPE_KEY) as CodeAnalysisTypes
        if (!analysisType) {
            steps.error("Unrecognized ANALYSIS TYPE -> ["+ analysisType +"]")
            return -1
        }
        String analysisToolPath = ""
        String analysisCommand = ""
        switch (analysisType) {
            case CodeAnalysisTypes.SONAR:
                analysisToolPath = analysis.get(CODE_ANALYSIS_SONAR_PATH_KEY)
                analysisCommand  = analysisToolPath + CODE_ANALYSIS_SONAR_COMMAND
                break
            default:
                break
        }
        if (!new File(analysisToolPath).exists()) {
            steps.error("** analysisType ** misconfigured -> "+ analysisToolPath + " not found.")
            return -1
        }
        int returnStatus = steps.sh(analysisCommand)
        if (returnStatus != 0) {
            steps.error("Sonar Scanner execution failed")
        }
        return returnStatus
    }

    @Override
    abstract int build()

    @Override
    abstract int publish(String registryType, String credentials)

    @Override
    abstract int deploy(String environment)

    @Override
    abstract int test(String type)
}
