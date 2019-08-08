package online.kairops.kna.skX.pipelines

import online.kairops.kna.skX.IStepExecutor
import online.kairops.kna.skX.ioc.ContextRegistry

class DockerPipeline extends AbstractCommonPipeline {

    public final static String BUILD_GOAL = "build"
    public final static String QA_GOAL = ""

    @Override
    int build() {
        IStepExecutor steps = ContextRegistry.getContext().getStepExecutor()

        int returnStatus = steps.docker(BUILD_GOAL)
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
