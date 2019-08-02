package online.kairops.kna.skX.pipelines

import online.kairops.kna.skX.IStepExecutor
import online.kairops.kna.skX.ioc.ContextRegistry

/**
 * Example class (without proper implementation) for using the MsBuild tool for building .NET projects.
 */
class MvnPipeline implements IPipeline,Serializable {

    public final static String BUILD_GOAL = "package"
    public final static String QA_GOAL = "sonar:sonar"

    @Override
    int qa(){

        IStepExecutor steps
        steps = ContextRegistry.getContext().getStepExecutor()

        int returnStatus = steps.mvn(QA_GOAL)
        if (returnStatus != 0) {
            steps.error("Some error")
        }
        return returnStatus
    }

    @Override
    int build() {
        IStepExecutor steps = ContextRegistry.getContext().getStepExecutor()

        int returnStatus = steps.mvn(BUILD_GOAL)
        if (returnStatus != 0) {
            steps.error("Some error")
        }
        return returnStatus
    }

    @Override
    int publish(String registryType, String credentials) {
        return 0
    }

    @Override
    int deploy(String environment) {
        return 0
    }

    @Override
    int test(String type) {
        return 0
    }
}
